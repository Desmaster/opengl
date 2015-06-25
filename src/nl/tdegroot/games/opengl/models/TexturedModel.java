package nl.tdegroot.games.opengl.models;

import nl.tdegroot.games.opengl.graphics.RawModel;
import nl.tdegroot.games.opengl.textures.ModelTexture;

public class TexturedModel {

    private RawModel model;
    private ModelTexture texture;

    public TexturedModel(RawModel model, ModelTexture texture) {
        this.model = model;
        this.texture = texture;
    }

    public RawModel getModel() {
        return model;
    }

    public ModelTexture getTexture() {
        return texture;
    }
}
