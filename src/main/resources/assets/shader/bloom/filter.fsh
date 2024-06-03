#version 330

in vec2 pass_textureUV;

out vec4 color;

uniform sampler2D textureSampler;

void main() {
    vec4 textureColor = texture(textureSampler, pass_textureUV);

    float brightColor = dot(textureColor.xyz, vec3(0.2126, 0.7152, 0.0722));

    color = textureColor * brightColor;
}