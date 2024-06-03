#version 330

layout(location = 0) in vec3 position;
layout(location = 1) in vec2 texture;

out vec2 pass_textureUV;

void main() {
    gl_Position = vec4(position, 1.0);
    pass_textureUV = texture;
}