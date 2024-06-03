#version 330

layout(location = 0) in vec3 position;

out vec2 passTextureUV;

uniform mat4 transformMatrix;
uniform vec4 textureUV;

void main() {
    gl_Position = transformMatrix * vec4(position, 1.0);
    float factorX = textureUV.z - textureUV.x;
    float factorY = textureUV.w - textureUV.y;
    passTextureUV = vec2(textureUV.x + ((position.x + 1.0) / 2.0) * factorX, 1.0 - textureUV.y - ((position.y + 1.0) / 2.0) * factorY);
}