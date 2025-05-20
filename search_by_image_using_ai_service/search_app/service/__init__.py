import os

import torch
from PIL import Image
from torchvision import transforms
from torchvision.models import resnet50, ResNet50_Weights

from search_app import PATH_FILE_MODEL

device = torch.device("cuda" if torch.cuda.is_available() else "cpu")

index_path = os.path.join(PATH_FILE_MODEL, "faiss_index.bin")
meta_path = os.path.join(PATH_FILE_MODEL, "image_data.pkl")

def config_model(pretrained_model):
    model = resnet50(weights=ResNet50_Weights.DEFAULT) if pretrained_model else resnet50(weights=ResNet50_Weights.IMAGENET1K_V2)
    model = torch.nn.Sequential(*list(model.children())[:-1])
    model.eval().to(device)
    return model


def extract_embedding(image_path, model):
    img = Image.open(image_path).convert("RGB")
    img_tensor = transform(img).unsqueeze(0).to(device)
    with torch.no_grad():
        emb = model(img_tensor)
    return emb.squeeze().cpu().numpy()


transform = transforms.Compose([
    transforms.Resize((224, 224)),
    transforms.ToTensor(),
    transforms.Normalize(
        mean=[0.485, 0.456, 0.406],
        std=[0.229, 0.224, 0.225]
    )
])
