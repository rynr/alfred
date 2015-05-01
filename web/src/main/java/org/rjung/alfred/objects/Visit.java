package org.rjung.alfred.objects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
public class Visit implements Printable {

    private static final int MIN_FONT_SIZE = 8;
    private static final int RES_MUL = 2; // 1 = 72 dpi; 4 = 288 dpi
    private static final int FONT_MUL = 2;
    private static final int BORDER = 3;
    private static final String DATE_FORMAT = "dd.MM.yy HH:mm";

    private static final Logger LOG = LoggerFactory.getLogger(Visit.class);

    @Id
    @GeneratedValue
    private Long id;

    @Column
    @NotNull
    @Size(min = 3, max = 24)
    private String name;

    @Column(name = "email")
    @NotNull
    @Email
    private String email;

    @Column(name = "company")
    @Size(min = 3, max = 24)
    private String company;

    @Column(updatable = false, name = "created_at", nullable = false)
    private Date createdAt;

    private static SimpleDateFormat dateFormat;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Date getCreatedAt() {
        return createdAt == null ? null : new Date(createdAt.getTime());
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt == null ? null : new Date(
                createdAt.getTime());
    }

    @PrePersist
    protected void beforeCreate() {
        if (createdAt == null)
            createdAt = new Date(System.currentTimeMillis());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        Visit other = (Visit) obj;
        return new EqualsBuilder().append(this.id, other.id)
                .append(this.name, other.name).append(this.email, other.email)
                .append(this.company, other.company)
                .append(this.createdAt, other.createdAt).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(name).append(email)
                .append(company).append(createdAt).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("name", name)
                .append("email", email).append("company", company)
                .append("createdAt", createdAt).toString();
    }

    @Override
    public int print(Graphics graphic, PageFormat format, int page)
            throws PrinterException {
        if (0 != page)
            return NO_SUCH_PAGE;
        int width = (int) Math.round(format.getImageableWidth() * RES_MUL) - 2
                * BORDER;
        int height = (int) Math.round(format.getImageableHeight() * RES_MUL)
                - 2 * BORDER;
        Graphics2D graphics = (Graphics2D) graphic;
        graphics.translate(format.getImageableX(), format.getImageableY());
        graphics.scale(1.0 / RES_MUL, 1.0 / RES_MUL);
        graphics.setColor(Color.black);
        graphics.drawRect(1, 1, width, height);
        placeText(graphics, name, 1, height / 2 + 1, Font.SANS_SERIF,
                Font.BOLD, 180, width * FONT_MUL, height * FONT_MUL / 2);
        placeText(graphics, company, 1, 3 * height / 4 + 1, Font.SANS_SERIF,
                Font.ITALIC, 120, width * FONT_MUL, height * FONT_MUL / 4);
        placeText(graphics, getDateFormat().format(createdAt), 1, height + 1,
                Font.SANS_SERIF, Font.PLAIN, 120, 2 * width * FONT_MUL / 3,
                height * FONT_MUL / 4);
        // placeText(graphics, "*" + id + "*", 1 + 2 * width / 3, height + 1,
        // "Free 3 of 9", Font.PLAIN, 120, width * FONT_MUL / 3, height);

        return PAGE_EXISTS;
    }

    private void placeText(Graphics2D graphics, String text, int x, int y,
            String fontName, int fontStyle, int maxFontSize, int width,
            int height) {
        graphics.setFont(getFontForText(text, fontName, fontStyle, maxFontSize,
                width, height / 3, graphics.getFontRenderContext()));
        graphics.drawString(text, x, y);
    }

    /**
     * Calculate the maximum sized {@link Font} to fit the text into a rectangle
     * of maximum <tt>width</tt>x<tt>height</tt>. There's a minimum font size (
     * {@link Visit#MIN_FONT_SIZE}) to be used even if the text does not fit.
     * 
     * @param text
     *            The text to be fitted in the rectangular space
     * @param fontName
     *            Name of the {@link Font} to use
     * @param fontStyle
     *            Style of the Font, like {@link Font#PLAIN},
     *            {@link Font#ITALIC} or{@link Font#BOLD}
     * @param maxFontSize
     *            Starting with this FontSize to check, then decrementing the
     *            size until it fits or the minimum FontSize (
     *            {@link Visit#MIN_FONT_SIZE}) is reached.
     * @param maxWidth
     *            The maximum Width of the resulting {@link Rectangle2D}.
     * @param maxHeight
     *            The maximum Height of the resulting {@link Rectangle2D}.
     * @return
     */
    private Font getFontForText(String text, String fontName, int fontStyle,
            int maxFontSize, int maxWidth, int maxHeight, FontRenderContext frc) {
        int checkSize = maxFontSize;
        LOG.error("Fit into " + maxWidth + "x" + maxHeight);
        while (true) {
            Font font = new Font(fontName, fontStyle, checkSize);

            Rectangle2D textSize = font.getStringBounds(text, frc);
            if (textSize.getWidth() <= maxWidth
                    && textSize.getHeight() <= maxHeight) {
                LOG.error("Size     " + textSize.getWidth() + "x"
                        + textSize.getHeight() + " / " + textSize.getMinX()
                        + "/" + textSize.getMinY());
                LOG.error("Font size for " + text + ": " + checkSize);
                return font;
            } else if (checkSize <= MIN_FONT_SIZE) {
                return font;
            } else {
                checkSize--;
            }
        }
    }

    public static SimpleDateFormat getDateFormat() {
        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat(DATE_FORMAT);
        }
        return dateFormat;
    }

}
