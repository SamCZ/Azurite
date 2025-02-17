package graphics;

import jdk.jfr.internal.Logger;
import org.joml.Vector2f;

/**
 * Contains the texture and UV coordinates used by openGL to render an image
 */
public class Sprite {
    /**
     * The texture for this sprite
     */
    protected Texture texture;

    /**
     * This sprite's texture coords
     */
    private Vector2f[] textureCoordinates;

    /**
     * This contains the default coordinates for an entire image UV
     */
    private Vector2f[] defaultTextureCoordinates = {
            new Vector2f(0, 0),
            new Vector2f(0, 1),
            new Vector2f(1, 1),
            new Vector2f(1, 0)
    };

    /**
     * Construct a Sprite using custom texture coordinates (uv).
     *
     * @param texture The texture for this sprite
     * @param uv      The sprite's texture coords
     */
    public Sprite(Texture texture, Vector2f[] uv) {
        this.texture = texture;
        this.textureCoordinates = uv;
    }

    /**
     * Construct a texture using default texture coordinates.
     *
     * @param texture The texture for this sprite
     */
    public Sprite(Texture texture) {
        this.texture = texture;
        this.textureCoordinates = defaultTextureCoordinates;
    }

    /**
     * Get this sprite's texture
     */
    public Texture getTexture() {
        return this.texture;
    }

    /**
     * Set this sprite's texture
     */
    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    /**
     * Get this sprite's texture coordinates
     */
    public Vector2f[] getTextureCoordinates() {
        return this.textureCoordinates;
    }

    /**
     * Get the texture id of this sprite
     */
    public int getTextureID() {
        return texture.getTextureID();
    }

    /**
     * Get the width of the Sprite
     */
    public float getWidth() {
        return texture.getWidth() * (textureCoordinates[0].x - textureCoordinates[3].x);
    }

    /**
     * Get the height of the Sprite
     */
    public float getHeight() {
        return texture.getHeight() * (textureCoordinates[1].y - textureCoordinates[0].y);
    }
}
