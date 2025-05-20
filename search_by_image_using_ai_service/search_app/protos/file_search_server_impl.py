import os

from search_app import PATH_FILE_MODEL
from search_app.protos import file_search_pb2_grpc, file_search_pb2
from search_app.protos.file_search_pb2 import SearchRequest
from search_app.service.query_similar import find_same_images


class FileSearchServerImpl(file_search_pb2_grpc.FileSearchServiceServicer):

    def Search(self, request: SearchRequest, context):
        path_file = os.path.join(PATH_FILE_MODEL, request.file_name)
        print(path_file)
        print(len(request.data))
        with open(path_file, "wb") as f:
            print("Start save file")
            f.write(b"".join(request.data))
            print("Save file successfully")

        try:
            result = find_same_images(path_file, top_k=30)
            return file_search_pb2.SearchResponse(ids=result)
        finally:
            # Dù có lỗi hay không thì vẫn xóa file tạm
            if os.path.exists(path_file):
                os.remove(path_file)
