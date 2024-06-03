#version 330

in vec2 passTextureUV;

out vec4 out_color;

uniform sampler2D textureSampler;
uniform vec4 color;

void main() {
    out_color = texture(textureSampler, passTextureUV) * color;
}

