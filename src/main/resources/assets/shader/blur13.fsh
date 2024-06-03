#version 330

in vec2 pass_textureUV;

out vec4 color;

uniform sampler2D textureSampler;
uniform bool horizontal;
uniform vec2 textureSize;

const float weight[] = float[] (0.0896631113333857,
    0.0874493212267511,
    0.0811305381519717,
    0.0715974486241365,
    0.0601029809166942,
    0.0479932050577658,
    0.0364543006660986,
    0.0263392293891488,
    0.0181026699707781,
    0.0118349786570722,
    0.0073599963704157,
    0.0043538453346397,
    0.0024499299678342);

void main() {
    vec4 result = texture(textureSampler, pass_textureUV) * weight[0];
    vec2 textureOffset = 1 / textureSize;

    if (horizontal) {
        for (int i = 1; i < weight.length(); ++i) {
            result += texture(textureSampler, pass_textureUV + vec2(textureOffset.x * i, 0.0)) * weight[i];
            result += texture(textureSampler, pass_textureUV - vec2(textureOffset.x * i, 0.0)) * weight[i];
        }
    } else {
        for (int i = 1; i < weight.length(); ++i) {
            result += texture(textureSampler, pass_textureUV + vec2(0.0, textureOffset.y * i)) * weight[i];
            result += texture(textureSampler, pass_textureUV - vec2(0.0, textureOffset.y * i)) * weight[i];
        }
    }

    color = result;
}