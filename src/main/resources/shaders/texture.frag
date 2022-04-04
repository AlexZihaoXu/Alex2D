#version 330 core

in vec2 vPos;
out vec4 FragColor;

uniform sampler2D texture0;
uniform vec4 rect;
uniform vec4 srcRect;
uniform float texWidth;
uniform float texHeight;
uniform vec4 color;

vec2 getCoord() {
    return vec2(
    (srcRect.r + srcRect.b * vPos.x) / int(texWidth),
    (srcRect.g + srcRect.a * vPos.y) / int(texHeight)
    );
}

void main() {
    vec4 destRect = rect;
    vec2 pos = getCoord();
    vec4 finalColor;
    if (texWidth - int(texWidth) > 0.25) {
        finalColor = texture(texture0, vec2(pos.x, 1 - pos.y));
    } else {
        finalColor = texture(texture0, pos);
    }
    if (finalColor.a < 1.0 / 255.0)
        discard;
    FragColor = finalColor * color;
}