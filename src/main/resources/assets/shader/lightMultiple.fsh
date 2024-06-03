#version 330

struct Material {
    vec3 color;
    vec3 specular;
    float shininess;
};

struct DirLight {
    vec3 direction;

    vec3 ambient;
    vec3 diffuse;
    vec3 specular;
};

struct PointLight {
    vec3 position;
    vec3 ambient;
    vec3 diffuse;
    vec3 specular;

//    float constant;
//    float linear;
//    float quadratic;
    float strength;
};

in VS_OUT {
    vec3 FragPos;
    vec3 Normal;
} fs_in;

layout(location = 0) out vec4 out_Color;
layout(location = 1) out vec4 out_Bright;

const float constant = 1.0f;
const float linear = 0.09f;
const float quadratic = 0.032f;

uniform vec3 viewPos;
uniform Material material;

uniform DirLight dirLight;
uniform PointLight pointLights[4];

vec3 calcDirLight(DirLight light, vec3 normal, vec3 viewPos);
vec3 calcPointLight(PointLight light, vec3 normal, vec3 viewPos);

void main() {
    vec3 norm = normalize(fs_in.Normal);
    vec3 viewDir = normalize(viewPos - fs_in.FragPos);

    vec3 result = calcDirLight(dirLight, norm, viewDir);

    for (int i = 0; i < 4; i++) {
        result += calcPointLight(pointLights[i], norm, viewDir);
    }

    out_Color = vec4(result, 1.0);

//    float brightColor = dot(result, vec3(0.2126, 0.7152, 0.0722));
//    if (brightColor > 0.95f) {
//        out_Bright = out_Color * max(brightColor, 1.0f);
//    }
    out_Bright = vec4(0);
}

vec3 calcDirLight(DirLight light, vec3 normal, vec3 viewPos) {
    vec3 lightDir = normalize(-light.direction);
    float diff = max(dot(normal, lightDir), 0.0);

    vec3 reflectDir = reflect(-lightDir, normal);
    float spec = pow(max(dot(viewPos, reflectDir), 0.0), material.shininess);

    vec3 ambient = light.ambient * material.color;
    vec3 diffuse = light.diffuse * (diff * material.color);
    vec3 specular = light.specular * (spec * material.specular);

    return (ambient + diffuse + specular);
}

vec3 calcPointLight(PointLight light, vec3 normal, vec3 viewPos) {
    vec3 lightDir = normalize(light.position - fs_in.FragPos);
    float diff = max(dot(normal, lightDir), 0.0);

    vec3 reflectDir = reflect(-lightDir, normal);
    float spec = pow(max(dot(viewPos, reflectDir), 0.0), material.shininess);

    float distance = length(light.position - fs_in.FragPos);
    float attenuation = light.strength / (constant + linear * distance + quadratic * distance * distance);

    vec3 ambient = light.ambient * material.color;
    vec3 diffuse = light.diffuse * (diff * material.color);
    vec3 specular = light.specular * (spec * material.specular);

    ambient *= attenuation;
    diffuse *= attenuation;
    specular *= attenuation;

    return (ambient + diffuse + specular);
}