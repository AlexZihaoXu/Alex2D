#version 330 core
layout (location = 0) in vec2 pos;
uniform vec3 circle;
uniform mat4 windowMat;
uniform mat4 transMat;

out float radius;
out vec2 f_pos;

void main() {
    radius = circle.z;
    f_pos = pos;
    gl_Position = windowMat * transMat * vec4(pos * 0.5 * radius + circle.xy, 0, 1);
}