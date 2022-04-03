package site.alex_xu.dev.alex2d.graphics.gl;

import org.apache.commons.io.IOUtils;
import org.joml.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

import static org.lwjgl.opengl.GL20.*;

public class Shader extends Freeable {
    private final int programID;
    private final ArrayList<Integer> shaderIds = new ArrayList<>();
    private final HashMap<String, Integer> uniformLocationMap = new HashMap<>();
    private boolean linked = false;

    public Shader() {
        programID = glCreateProgram();
        if (programID == 0) {
            System.err.println("Unable to create shader program!");
            throw new IllegalStateException("Unable to create shader program!");
        }
    }

    public int getUniformLocation(String uniformName) {
        if (!uniformLocationMap.containsKey(uniformName)) {
            uniformLocationMap.put(uniformName, glGetUniformLocation(programID, uniformName));
        }
        int location = uniformLocationMap.get(uniformName);
        if (location == -1)
            throw new IllegalStateException("No uniform named: " + uniformName);
        return location;
    }

    public boolean hasUniform(String name) {
        if (!uniformLocationMap.containsKey(name)) {
            uniformLocationMap.put(name, glGetUniformLocation(programID, name));
        }
        int location = uniformLocationMap.get(name);

        return location != -1;
    }

    // Setters

    private final HashMap<String, Integer> setIntCache = new HashMap<>();

    public Shader setInt(String uniformName, int value) {
        setIntCache.putIfAbsent(uniformName, Integer.MAX_VALUE);
        if (setIntCache.get(uniformName) != value) {
            bind();
            glUniform1i(getUniformLocation(uniformName), value);
            setIntCache.put(uniformName, value);
        }
        return this;
    }

    private final HashMap<String, Float> setFloatCache = new HashMap<>();

    public Shader setFloat(String uniformName, float value) {
        setFloatCache.putIfAbsent(uniformName, Float.MAX_VALUE);
        if (setFloatCache.get(uniformName) != value) {
            bind();
            glUniform1f(getUniformLocation(uniformName), value);
            setFloatCache.put(uniformName, value);
        }
        return this;
    }

    private final HashMap<String, Vector2f> setVec2Cache = new HashMap<>();

    public Shader setVec2(String uniformName, float x, float y) {
        setVec2Cache.putIfAbsent(uniformName, new Vector2f(Float.MAX_VALUE, Float.MAX_VALUE));
        Vector2f vec = setVec2Cache.get(uniformName);
        if (vec.x != x || vec.y != y) {
            bind();
            glUniform2f(getUniformLocation(uniformName), x, y);
            vec.x = x;
            vec.y = y;
        }
        return this;
    }

    private final HashMap<String, Vector3f> setVec3Cache = new HashMap<>();

    public Shader setVec3(String uniformName, float x, float y, float z) {
        setVec3Cache.putIfAbsent(uniformName, new Vector3f(Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE));
        Vector3f vec = setVec3Cache.get(uniformName);
        if (vec.x != x || vec.y != y || vec.z != z) {
            bind();
            glUniform3f(getUniformLocation(uniformName), x, y, z);
            vec.x = x;
            vec.y = y;
            vec.z = z;
        }
        return this;
    }

    private final HashMap<String, Vector4f> setVec4Cache = new HashMap<>();

    public Shader setVec4(String uniformName, float x, float y, float z, float w) {
        setVec4Cache.putIfAbsent(uniformName, new Vector4f(Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE));
        Vector4f vec = setVec4Cache.get(uniformName);
        if (vec.x != x || vec.y != y || vec.z != z || vec.w != w) {
            bind();
            glUniform4f(getUniformLocation(uniformName), x, y, z, w);
            vec.x = x;
            vec.y = y;
            vec.z = z;
            vec.w = w;
        }
        return this;
    }

    private final HashMap<String, Matrix4f> setMat4Cache = new HashMap<>();

    public Shader setMat4(String uniformName, boolean transpose, Matrix4f mat4) {
        setMat4Cache.putIfAbsent(uniformName, new Matrix4f(
                Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE,
                Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE,
                Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE,
                Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE
        ));
        Matrix4f mat = setMat4Cache.get(uniformName);
        if (!mat4.equals(mat)) {
            bind();
            glUniformMatrix4fv(getUniformLocation(uniformName), transpose, mat4.get(new float[16]));
            mat.set(mat4);
        }
        return this;
    }

    // Constructing

    private void addShader(int type, String source, String path) {
        int shaderID = glCreateShader(type);
        if (shaderID == 0) {
            System.err.println("Unable to create shader!");
            throw new IllegalStateException("Unable to create shader!");
        }

        String shaderTypeName = type == GL_VERTEX_SHADER ? "vertex" : "fragment";

        glShaderSource(shaderID, source);
        glCompileShader(shaderID);

        if (glGetShaderi(shaderID, GL_COMPILE_STATUS) == 0) {
            System.err.println("Failed to compile: " + path);
            String message = "Failed to compile " + shaderTypeName + " shader: " + glGetShaderInfoLog(shaderID);
            System.err.println(message);
            glDeleteShader(shaderID);
            throw new RuntimeException(message);
        }

        glAttachShader(programID, shaderID);
        shaderIds.add(shaderID);
    }

    public Shader link() {
        if (linked) {
            throw new IllegalStateException("Cannot link program twice!");
        }
        glLinkProgram(programID);
        if (glGetProgrami(programID, GL_LINK_STATUS) == 0) {
            String message = "Failed to link shader program: " + glGetProgramInfoLog(programID);
            System.err.println(message);
            throw new IllegalStateException(message);
        }
        for (Integer id : this.shaderIds) {
            glDeleteShader(id);
        }
        linked = true;
        return this;
    }

    public Shader addFromResource(String filename) {
        if (linked) {
            throw new IllegalStateException("This shader program is already linked!");
        }
        if (filename.endsWith(".vert") || filename.endsWith(".frag")) {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename);
            if (inputStream == null) {
                System.err.println("Can't find shader from: " + filename);
                throw new RuntimeException("Can't find shader from: " + filename);
            }
            try {
                String source = IOUtils.toString(inputStream);
                inputStream.close();
                addShader(filename.endsWith(".vert") ? GL_VERTEX_SHADER : GL_FRAGMENT_SHADER, source, filename);
                return this;
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.err.println("Unable load shader source: " + filename);
            throw new RuntimeException("Unable load shader source: " + filename);
        } else {
            System.err.println("Unable to determine shader type: extension should either be .vert or .frag!");
            throw new IllegalStateException("Unable to determine shader type: extension should either be .vert or .frag!");
        }
    }

    public Shader addFromPath(String filename) {
        if (linked) {
            throw new IllegalStateException("This shader program is already linked!");
        }
        if (filename.endsWith(".vert") || filename.endsWith(".frag")) {
            InputStream inputStream = null;
            try {
                inputStream = new FileInputStream(filename);
            } catch (FileNotFoundException ignored) {
            }
            if (inputStream == null) {
                System.err.println("Can't find shader from: " + filename);
                throw new RuntimeException("Can't find shader from: " + filename);
            }
            try {
                String source = IOUtils.toString(inputStream);
                inputStream.close();
                addShader(filename.endsWith(".vert") ? GL_VERTEX_SHADER : GL_FRAGMENT_SHADER, source, filename);
                return this;
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.err.println("Unable load shader source: " + filename);
            throw new RuntimeException("Unable load shader source: " + filename);
        } else {
            System.err.println("Unable to determine shader type: extension should either be .vert or .frag!");
            throw new IllegalStateException("Unable to determine shader type: extension should either be .vert or .frag!");
        }
    }

    private static Shader boundShader = null;

    public void bind() {
        if (boundShader != this) {
            glUseProgram(programID);
            boundShader = this;
        }
    }

    public void unbind() {
        glUseProgram(0);
        boundShader = null;
    }

    @Override
    protected void onDispose() {
        freedShaders.add(programID);
    }
}