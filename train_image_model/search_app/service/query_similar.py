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
    return index, data["image_paths"], data["labels"]


def find_similar_images(query_image_path, top_k=5):
    index, image_paths, labels = load_index()
    model = config_model(pretrained_model=False)
    query_emb = extract_embedding(query_image_path, model).reshape(1, -1).astype("float32")
    distances, indices = index.search(query_emb, top_k)

    print(f"\n🔍 Kết quả tìm kiếm cho ảnh '{query_image_path}':")
    for i, idx in enumerate(indices[0]):
        category, product = labels[idx].split("___")
        path = image_paths[idx]
        print(f"{i + 1}. Category: {category} | Product: {product} | Distance: {distances[0][i]:.4f}")


if __name__ == "__main__":
    default_path = "D:\\tai_lieu_hoc_tap\\chuyen_de_web\\fashion_e_commerce\\resources\\quan-short-nam"
    find_similar_images(
        "../data/test_data/test_3.jpg",
        top_k=10)
