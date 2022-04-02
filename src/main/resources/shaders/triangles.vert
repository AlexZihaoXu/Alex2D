#version 330 core
layout (location = 0) in float index;
uniform vec2 pos1;
uniform vec2 pos2;
uniform vec2 pos3;
uniform mat4 windowMat;
uniform mat4 transMat;

void main() {
    vec2 pos;
    int index = int(index);
    if (index == 0)
        pos = pos1;
    else if (index == 1)
        pos = pos2;
    else if (index == 2)
        pos = pos3;
    gl_Position = windowMat * transMat * vec4(pos, 0, 1);
}