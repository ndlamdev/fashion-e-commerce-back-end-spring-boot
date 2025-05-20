import os

from search_app.service import extract_embedding, config_model, index_path, meta_path

os.environ["KMP_DUPLICATE_LIB_OK"] = "TRUE"  # Fix lỗi OpenMP

import pickle
import faiss


# ====== CONFIG ======
def load_index():
    if not os.path.exists(index_path) or not os.path.exists(meta_path):
        raise FileNotFoundError("⚠️ FAISS index hoặc metadata chưa được tạo. Hãy chạy train_and_save.py trước.")

    index = faiss.read_index(index_path)
    with open(meta_path, "rb") as f:
        data = pickle.load(f)
    return index, data["Image"], data["labels"]

index_in_model, image_in_model, labels_in_model = load_index()

def find_same_images(query_image_path: str, top_k=5) -> list[str]:
    model = config_model(pretrained_model=False)
    query_emb = extract_embedding(query_image_path, model).reshape(1, -1).astype("float32")
    distances, indices = index_in_model.search(query_emb, top_k)

    print(f"\n🔍 Kết quả tìm kiếm cho ảnh '{query_image_path}':")
    result = []
    for i, idx in enumerate(indices[0]):
        if distances[0][i] > 100:
            continue
        result.append(image_in_model[idx])
        print(f"{i + 1}. Image {image_in_model[idx]} | Distance: {distances[0][i]:.4f}")

    return result


if __name__ == "__main__":
    find_same_images(
        "../data/test/test.jpg",
        top_k=10)
