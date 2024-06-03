#version 330

layout(location = 0) out vec4 out_Color;
layout(location = 1) out vec4 out_Bright;

uniform vec3 color;

void main(){
    out_Color = vec4(color, 1.0);

    float brightColor = dot(color, vec3(0.2126, 0.7152, 0.0722));

    out_Bright = out_Color * brightColor;
}