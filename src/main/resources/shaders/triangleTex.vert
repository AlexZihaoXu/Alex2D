#version 330 core

layout (location = 0) in float index;
uniform vec4 pos1;
uniform vec4 pos2;
uniform vec4 pos3;
uniform mat4 windowMat;
uniform mat4 transMat;

out vec2 texCoord;

void main() {
    vec4 pos;
    int index = int(index);
    if (index == 0)
    pos = pos1;
    else if (index == 1)
    pos = pos2;
    else if (index == 2)
    pos = pos3;
    texCoord = pos.zw;
    gl_Position = windowMat * transMat * vec4(pos.xy, 0, 1);
}