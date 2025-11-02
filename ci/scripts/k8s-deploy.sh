#!/bin/bash
set -e

echo "🚀 Deploying to Kubernetes..."

kubectl apply -f k8s/namespace.yaml
kubectl apply -f k8s/configmap.yaml
kubectl apply -f k8s/secrets.yaml
kubectl apply -f k8s/deployment.yaml
kubectl apply -f k8s/service.yaml
kubectl apply -f k8s/hpa.yaml

echo "⏳ Waiting for deployment..."
kubectl rollout status deployment/ftms-backend -n ftms --timeout=5m

echo "✅ Deployment completed"