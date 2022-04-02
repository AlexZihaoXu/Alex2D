#version 330 core

in vec2 vPos;
out vec4 FragColor;

uniform vec4 color;
uniform sampler2D texture0;
uniform vec4 rect;
uniform vec4 srcRect;
uniform float texWidth;
uniform float texHeight;

vec2 getCoord() {
    return vec2(
    (srcRect.r + srcRect.b * vPos.x) / texWidth,
    (srcRect.g + srcRect.a * vPos.y) / texHeight
    );
}

void main() {
    vec4 destRect = rect;
    vec2 pos = getCoord();
    FragColor = texture(texture0, vec2(pos.x, pos.y));
}