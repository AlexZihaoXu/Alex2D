#version 330 core

uniform vec4 color;
in float radius;
in vec2 f_pos;
out vec4 FragColor;

void main() {
    float x = f_pos.x;
    float y = f_pos.y;
    float distance = (x * x + y * y);
    if (distance > 1) {
        discard;
    }
//    float delta = fwidth(distance);
//    float alpha = 1 - smoothstep(1 / 1.25 -delta, 1 / 1.25, distance);
//    vec4 fColor = vec4(color.rgb, color.a * alpha);
    FragColor = color;
}
