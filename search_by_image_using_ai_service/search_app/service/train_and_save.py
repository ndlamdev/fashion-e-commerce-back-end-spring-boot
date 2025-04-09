import os
import pickle

import faiss
import numpy as np

from search_app.service import config_model, extract_embedding, index_path, meta_path

os.environ["KMP_DUPLICATE_LIB_OK"] = "TRUE"

# ====== CONFIG ======
root_dir = "../data/train_data"  # Thư mục chứa ảnh


def build_and_save_index():
    image_paths = []
    labels = []
    embeddings = []
    model = config_model(pretrained_model=True)

    print("🔍 Đang trích xuất đặc trưng và xây index...")
    for category in os.listdir(root_dir):
        path_category = os.path.join(root_dir, category)
        for product in os.listdir(path_category):
            path_product = os.path.join(path_category, product)
            label = f"{category}___{product}"
            for file in os.listdir(path_product):
                if file.lower().endswith(('.jpg', '.jpeg', '.png')):
                    path_file = os.path.join(path_product, file)
                    image_paths.append(path_file)
                    labels.append(label)
                    emb = extract_embedding(path_file, model)
                    embeddings.append(emb)

    embeddings = np.stack(embeddings).astype("float32")
    index = faiss.IndexFlatL2(embeddings.shape[1])
    index.add(embeddings)

    # Save index & metadata
    faiss.write_index(index, index_path)
    with open(meta_path, "wb") as f:
        pickle.dump({"image_paths": image_paths, "labels": labels}, f)

    print(f"✅ Đã lưu FAISS index ({len(image_paths)} ảnh) vào '{index_path}'")
    print(f"✅ Đã lưu metadata vào '{meta_path}'")


if __name__ == "__main__":
    build_and_save_index()
