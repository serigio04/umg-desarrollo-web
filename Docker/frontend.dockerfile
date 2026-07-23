# Etapa 1: Compilación
FROM node:20-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build -- --configuration=production

# Etapa 2: Servidor web ultraligero
# Etapa 2: Servidor web ultraligero
FROM nginx:1.25-alpine
# ¡Asegúrate de cambiar 'frontend' por 'frontend-app' en la siguiente línea!
COPY --from=build /app/dist/frontend-app/browser /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]