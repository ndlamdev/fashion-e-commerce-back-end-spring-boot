from django.views.decorators.http import require_http_methods

from search_app.service import config_model, extract_embedding
from search_app.service.query_similar import load_index

# Create your views here.
index, image_paths, labels = load_index()
model = config_model(pretrained_model=False)

@require_http_methods(["GET", "POST"])
def search(request):
    query_image_path = request.GET.get('query_image_path')
    top_k = 10
    query_emb = extract_embedding(query_image_path, model).reshape(1, -1).astype("float32")
    distances, indices = index.search(query_emb, top_k)

    print(f"\n🔍 Kết quả tìm kiếm cho ảnh '{query_image_path}':")
    for i, idx in enumerate(indices[0]):
        print(f"{i + 1}. {image_paths[idx]} | Label: {labels[idx]} | Distance: {distances[0][i]:.4f}")
