#version 330

in vec2 pass_textureUV;

out vec4 color;

uniform sampler2D textureSampler;
uniform bool horizontal;
uniform vec2 textureSize;

const float weight[5] = float[] (0.2270270270, 0.1945945946, 0.1216216216, 0.0540540541, 0.0162162162);

void main() {
    vec4 result = texture(textureSampler, pass_textureUV) * weight[0];
    vec2 textureOffset = 1 / textureSize;

    if (horizontal) {
        for (int i = 1; i < 5; ++i) {
            result += texture(textureSampler, pass_textureUV + vec2(textureOffset.x * i, 0.0)) * weight[i];
            result += texture(textureSampler, pass_textureUV - vec2(textureOffset.x * i, 0.0)) * weight[i];
        }
    } else {
        for (int i = 1; i < 5; ++i) {
            result += texture(textureSampler, pass_textureUV + vec2(0.0, textureOffset.y * i)) * weight[i];
            result += texture(textureSampler, pass_textureUV - vec2(0.0, textureOffset.y * i)) * weight[i];
        }
    }

    color = result;
}