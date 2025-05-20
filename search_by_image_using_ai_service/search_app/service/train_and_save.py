import os
import pickle

import faiss
import numpy as np

from search_app.service import config_model, extract_embedding, index_path, meta_path

os.environ["KMP_DUPLICATE_LIB_OK"] = "TRUE"

# ====== CONFIG ======
root_dir = "D:\\tai_lieu_hoc_tap\\chuyen_de_web\\fashion_e_commerce\\resources"  # Thư mục chứa ảnh


def build_and_save_index():
    image_names = []
    labels = []
    embeddings = []
    model = config_model(pretrained_model=True)

    print("🔍 Đang trích xuất đặc trưng và xây index...")
    total_files = len(os.listdir(root_dir))
    trained_file = 0
    before= 0
    for file in os.listdir(root_dir):
        trained_file += 1
        current = round((trained_file / total_files), 3)
        if before != current:
            print("Processing {}%".format(current * 100))
            before = current

        path_file = os.path.join(root_dir, file)
        image_names.append(file)
        labels.append(file)
        emb = extract_embedding(path_file, model)
        embeddings.append(emb)

    embeddings = np.stack(embeddings).astype("float32")
    index = faiss.IndexFlatL2(embeddings.shape[1])
    index.add(embeddings)

    # Save index & metadata
    faiss.write_index(index, index_path)
    with open(meta_path, "wb") as f:
        pickle.dump({"Image": image_names, "labels": labels}, f)

    print(f"✅ Đã lưu FAISS index ({len(image_names)} ảnh) vào '{index_path}'")
    print(f"✅ Đã lưu metadata vào '{meta_path}'")


if __name__ == "__main__":
    build_and_save_index()
