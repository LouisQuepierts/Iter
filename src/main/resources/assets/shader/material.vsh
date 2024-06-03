#version 330

layout(location = 0) in vec3 position;
layout(location = 1) in vec2 textureCoords;
layout(location = 2) in vec3 normal;

out VS_OUT {
    vec3 FragPos;
    vec3 Normal;
} vs_out;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

void main() {
    vs_out.FragPos = vec3(transformationMatrix * vec4(position, 1.0));
    vs_out.Normal = mat3(transpose(inverse(transformationMatrix))) * normal;

    gl_Position = projectionMatrix * viewMatrix * vec4(vs_out.FragPos, 1.0);
}