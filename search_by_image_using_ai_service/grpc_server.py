import os

import grpc
from concurrent import futures

from search_app import PATH_FILE_TEMP
from search_app.protos import file_search_pb2_grpc
from search_app.protos.file_search_server_impl import FileSearchServerImpl
from search_app.eureka_client import register_to_eureka


def serve():
    os.makedirs(PATH_FILE_TEMP, exist_ok=True)
    port = 8113
    register_to_eureka("fashion-e-commerce-file-search-service", port)
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    file_search_pb2_grpc.add_FileSearchServiceServicer_to_server(FileSearchServerImpl(), server)
    server.add_insecure_port(f'[::]:{port}')
    server.start()
    print(f"Python gRPC server is running on port {port}...")
    server.wait_for_termination()


if __name__ == '__main__':
    serve()
