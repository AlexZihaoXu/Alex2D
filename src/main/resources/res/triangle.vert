#version 330 core
layout (location = 0) in float index;
uniform mat4 orthoMat;
uniform mat4 transMat;
uniform vec2 p1;

void main() {

    vec2 position;
    int i = int(index);
    if (i == 0) {
        position = triangle[0];
    }

    gl_Position = orthoMat * transMat * vec4(position, 0.0, 1.0);
}
