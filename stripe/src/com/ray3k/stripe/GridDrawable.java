package com.ray3k.stripe;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * An implmentation of Drawable that creates a square grid of nine child drawables. The drawables on the corners are
 * drawn at minimum sizes. The drawables on the center edges are stretched in one dimension only. The center drawable is
 * stretched to the maximum size required to fill the remaining space. The size of a border is defined by the largest
 * minimum size along that edge (ie. the top border height is defined by the tallest drawable among topLeft, top, and
 * topRight). This is most useful for manually creating nine patches from unconventional drawable sources.
 */
public class GridDrawable implements Drawable {
    public Drawable topLeft;
    public Drawable top;
    public Drawable topRight;
    public Drawable left;
    public Drawable center;
    public Drawable right;
    public Drawable bottomLeft;
    public Drawable bottom;
    public Drawable bottomRight;
    private float leftWidth;
    private float rightWidth;
    private float topHeight;
    private float bottomHeight;
    private float minWidth;
    private float minHeight;
    private float leftMinWidth;
    private float centerMinWidth;
    private float rightMinWidth;
    private float topMinHeight;
    private float centerMinHeight;
    private float bottomMinHeight;
    
    /**
     * A no-argument constructor necessary for serialization.
     */
    public GridDrawable() {
    }
    
    /**
     * Creates a grid drawable from the specified child drawables. Arguments may be null in which case they will be
     * considered as a zero width/height.
     * @param topLeft
     * @param top
     * @param topRight
     * @param left
     * @param center
     * @param right
     * @param bottomLeft
     * @param bottom
     * @param bottomRight
     */
    public GridDrawable(Drawable topLeft, Drawable top, Drawable topRight,
                        Drawable left, Drawable center, Drawable right,
                        Drawable bottomLeft, Drawable bottom, Drawable bottomRight) {
        this.topLeft = topLeft;
        this.top = top;
        this.topRight = topRight;
        this.left = left;
        this.center = center;
        this.right = right;
        this.bottomLeft = bottomLeft;
        this.bottom = bottom;
        this.bottomRight = bottomRight;
        calcMinSize();
    }
    
    /**
     * Creates a new empty GridDrawable with the same sizing information and child drawables as the specified drawable.
     *
     * @param drawable
     */
    public GridDrawable(GridDrawable drawable) {
        leftWidth = drawable.getLeftWidth();
        rightWidth = drawable.getRightWidth();
        topHeight = drawable.getTopHeight();
        bottomHeight = drawable.getBottomHeight();
        minWidth = drawable.getMinWidth();
        minHeight = drawable.getMinHeight();
        topLeft = drawable.topLeft;
        top = drawable.top;
        topRight = drawable.topRight;
        left = drawable.left;
        center = drawable.center;
        right = drawable.right;
        bottomLeft = drawable.bottomLeft;
        bottom = drawable.bottom;
        bottomRight = drawable.bottomRight;
        calcMinSize();
    }
    
    private void calcMinSize() {
        leftMinWidth = Math.max(left == null ? 0 : left.getMinWidth(), topLeft == null ? 0 : topLeft.getMinWidth());
        leftMinWidth = Math.max(leftMinWidth, bottomLeft == null ? 0 : bottomLeft.getMinWidth());
    
        centerMinWidth = Math.max(top == null ? 0 : top.getMinWidth(), center == null ? 0 : center.getMinWidth());
        centerMinWidth = Math.max(centerMinWidth, bottom == null ? 0 : bottom.getMinWidth());
        
        rightMinWidth = Math.max(right == null ? 0 : right.getMinWidth(), topRight == null ? 0 : topRight.getMinWidth());
        rightMinWidth = Math.max(rightMinWidth, bottomRight == null ? 0 : bottomRight.getMinWidth());
        
        minWidth = leftMinWidth + centerMinWidth + rightMinWidth;
        
        topMinHeight = Math.max(top == null ? 0 : top.getMinHeight(), topLeft == null ? 0 : topLeft.getMinHeight());
        topMinHeight = Math.max(topMinHeight, topRight == null ? 0 : topRight.getMinHeight());
        
        centerMinHeight = Math.max(center == null ? 0 : center.getMinHeight(), left == null ? 0 : left.getMinHeight());
        centerMinHeight = Math.max(centerMinHeight, right == null ? 0 : right.getMinHeight());
    
        bottomMinHeight = Math.max(bottom == null ? 0 : bottom.getMinHeight(), bottomLeft == null ? 0 : bottomLeft.getMinHeight());
        bottomMinHeight = Math.max(bottomMinHeight, bottomRight == null ? 0 : bottomRight.getMinHeight());
        
        minHeight = topMinHeight + centerMinHeight + bottomMinHeight;
    }
    
    @Override
    public void draw(Batch batch, float x, float y, float width, float height) {
        float centerHeight = height - bottomMinHeight - topMinHeight;
        if (topLeft != null) topLeft.draw(batch, x, y + height - topMinHeight, leftMinWidth, topMinHeight);
        if (bottomLeft != null) bottomLeft.draw(batch, x, y, leftMinWidth, bottomMinHeight);
        if (left != null) left.draw(batch, x, y + bottomMinHeight, leftMinWidth, centerHeight);
        
        float centerWidth = width - leftMinWidth - rightMinWidth;
        if (top != null) top.draw(batch, x + leftMinWidth, y + height - topMinHeight, centerWidth, topMinHeight);
        if (bottom != null) bottom.draw(batch, x + leftMinWidth, y, centerWidth, bottomMinHeight);
        if (center != null) center.draw(batch, x + leftMinWidth, y + bottomMinHeight, centerWidth, centerHeight);
    
        if (topRight != null) topRight.draw(batch, x + width - rightMinWidth, y + height - topMinHeight, rightMinWidth, topMinHeight);
        if (bottomRight != null) bottomRight.draw(batch, x + width - rightMinWidth, y, rightMinWidth, bottomMinHeight);
        if (right != null) right.draw(batch, x + width - rightMinWidth, y + bottomMinHeight, rightMinWidth, centerHeight);
    }
    
    @Override
    public float getLeftWidth() {
        return leftWidth;
    }
    
    @Override
    public void setLeftWidth(float leftWidth) {
        this.leftWidth = leftWidth;
    }
    
    @Override
    public float getRightWidth() {
        return rightWidth;
    }
    
    @Override
    public void setRightWidth(float rightWidth) {
        this.rightWidth = rightWidth;
    }
    
    @Override
    public float getTopHeight() {
        return topHeight;
    }
    
    @Override
    public void setTopHeight(float topHeight) {
        this.topHeight = topHeight;
    }
    
    @Override
    public float getBottomHeight() {
        return bottomHeight;
    }
    
    @Override
    public void setBottomHeight(float bottomHeight) {
        this.bottomHeight = bottomHeight;
    }
    
    @Override
    public float getMinWidth() {
        return minWidth;
    }
    
    @Override
    public void setMinWidth(float minWidth) {
        this.minWidth = minWidth;
    }
    
    @Override
    public float getMinHeight() {
        return minHeight;
    }
    
    @Override
    public void setMinHeight(float minHeight) {
        this.minHeight = minHeight;
    }
    
    public Drawable getTopLeft() {
        return topLeft;
    }
    
    public void setTopLeft(Drawable topLeft) {
        this.topLeft = topLeft;
        calcMinSize();
    }
    
    public Drawable getTop() {
        return top;
    }
    
    public void setTop(Drawable top) {
        this.top = top;
        calcMinSize();
    }
    
    public Drawable getTopRight() {
        return topRight;
    }
    
    public void setTopRight(Drawable topRight) {
        this.topRight = topRight;
        calcMinSize();
    }
    
    public Drawable getLeft() {
        return left;
    }
    
    public void setLeft(Drawable left) {
        this.left = left;
        calcMinSize();
    }
    
    public Drawable getCenter() {
        return center;
    }
    
    public void setCenter(Drawable center) {
        this.center = center;
        calcMinSize();
    }
    
    public Drawable getRight() {
        return right;
    }
    
    public void setRight(Drawable right) {
        this.right = right;
        calcMinSize();
    }
    
    public Drawable getBottomLeft() {
        return bottomLeft;
    }
    
    public void setBottomLeft(Drawable bottomLeft) {
        this.bottomLeft = bottomLeft;
        calcMinSize();
    }
    
    public Drawable getBottom() {
        return bottom;
    }
    
    public void setBottom(Drawable bottom) {
        this.bottom = bottom;
        calcMinSize();
    }
    
    public Drawable getBottomRight() {
        return bottomRight;
    }
    
    public void setBottomRight(Drawable bottomRight) {
        this.bottomRight = bottomRight;
        calcMinSize();
    }
}
