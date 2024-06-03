#version 330

in vec2 pass_textureUV;

out vec4 color;

uniform sampler2D textureSampler;
uniform sampler2D blurSampler;
uniform float gamma;
uniform float exposure;

void main() {
    vec3 textureColor = texture(textureSampler, pass_textureUV).rgb;
    vec3 blurColor = texture(blurSampler, pass_textureUV).rgb;

    vec3 result = textureColor + blurColor;

    vec3 mapped = vec3(1.0) - exp(-result * exposure);

    mapped = pow(mapped, vec3(1.0 / gamma));

    color = vec4(mapped, 1.0);
}
