package com.anychart.core.utils;

import com.anychart.APIlib;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.JsObject;
import com.anychart.core.utils.Interactivity;

import java.util.Locale;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import android.text.TextUtils;

// class
/**
 * Interactivity class for maps.<br/>
{docs:Maps/Move_and_Zoom#navigational_interactivity}Learn more about the map interactivity.{docs}
 */
public class MapInteractivity extends Interactivity {

    protected MapInteractivity() {

    }

    public static MapInteractivity instantiate() {
        return new MapInteractivity("new anychart.core.utils.mapInteractivity()");
    }

    

    public MapInteractivity(String jsChart) {
        jsBase = "mapInteractivity" + ++variableIndex;
        APIlib.getInstance().addJSLine(jsBase + " = " + jsChart + ";");
    }

    public String getJsBase() {
        return jsBase;
    }

    
    /**
     * 
     */
    public com.anychart.core.utils.MapInteractivity allowMultiSeriesSelection(Boolean value) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".allowMultiSeriesSelection(%s);", value));

        return this;
    }
    /**
     * Getter for the copy formatter function.
     */
    public void copyFormat() {
        APIlib.getInstance().addJSLine(jsBase + ".copyFormat();");
    }
    /**
     * Setter for the copy formatter function.<br/>
{docs:Common_Settings/Text_Formatters}Learn more about using copyFormat() method.{docs}
     */
    public com.anychart.core.utils.MapInteractivity copyFormat(String value) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".copyFormat(%s);", wrapQuotes(value)));

        return this;
    }
    /**
     * Getter for the dragging value.
     */
    public void drag() {
        APIlib.getInstance().addJSLine(jsBase + ".drag();");
    }
    /**
     * Allows to use drag for map.
<b>Note:</b> Works only with {@link anychart.charts.Map#zoom}
     */
    public com.anychart.core.utils.MapInteractivity drag(Boolean value) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".drag(%s);", value));

        return this;
    }
    /**
     * Getter for the hover mode.
     */
    public void hoverMode() {
        APIlib.getInstance().addJSLine(jsBase + ".hoverMode();");
    }
    /**
     * Setter for the hover mode.
     */
    public com.anychart.core.utils.MapInteractivity hoverMode(com.anychart.enums.HoverMode value) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".hoverMode(%s);", (value != null) ? value.getJsBase() : null));

        return this;
    }
    /**
     * Setter for the hover mode.
     */
    public com.anychart.core.utils.MapInteractivity hoverMode(String value) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".hoverMode(%s);", wrapQuotes(value)));

        return this;
    }
    /**
     * Getter for the keyboard settings.
     */
    public void keyboardZoomAndMove() {
        APIlib.getInstance().addJSLine(jsBase + ".keyboardZoomAndMove();");
    }
    /**
     * Allows to use the keyboard to zoom and move.
     */
    public com.anychart.core.utils.MapInteractivity keyboardZoomAndMove(Boolean value) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".keyboardZoomAndMove(%s);", value));

        return this;
    }
    /**
     * 
     */
    public void mouseWheel() {
        APIlib.getInstance().addJSLine(jsBase + ".mouseWheel();");
    }
    /**
     * 
     */
    public com.anychart.core.utils.MapInteractivity mouseWheel(Boolean value) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".mouseWheel(%s);", value));

        return this;
    }
    /**
     * Getter for the multi-select on click.
     */
    public void multiSelectOnClick() {
        APIlib.getInstance().addJSLine(jsBase + ".multiSelectOnClick();");
    }
    /**
     * Setter for the multi-select on click.
     */
    public com.anychart.core.utils.MapInteractivity multiSelectOnClick(Boolean value) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".multiSelectOnClick(%s);", value));

        return this;
    }
    /**
     * Removes all listeners from an object. You can also optionally remove listeners of some particular type.
     */
    public void removeAllListeners(String type) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".removeAllListeners(%s);", wrapQuotes(type)));
    }
    /**
     * Getter for the selection mode.
     */
    public void selectionMode() {
        APIlib.getInstance().addJSLine(jsBase + ".selectionMode();");
    }
    /**
     * Setter for the selection mode.
     */
    public com.anychart.core.utils.MapInteractivity selectionMode(com.anychart.enums.SelectionMode value) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".selectionMode(%s);", (value != null) ? value.getJsBase() : null));

        return this;
    }
    /**
     * Setter for the selection mode.
     */
    public com.anychart.core.utils.MapInteractivity selectionMode(String value) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".selectionMode(%s);", wrapQuotes(value)));

        return this;
    }
    /**
     * Getter for the spot radius.
     */
    public void spotRadius() {
        APIlib.getInstance().addJSLine(jsBase + ".spotRadius();");
    }
    /**
     * Setter for the spot radius.
Size of the area under cursor in pixels for radius hovering.
<b>Note:</b> Works only with "by-spot" value in the {@link anychart.core.utils.Interactivity#hoverMode} method.
     */
    public com.anychart.core.utils.MapInteractivity spotRadius(Number value) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".spotRadius(%s);", value));

        return this;
    }
    public void setOnClickListener(com.anychart.chart.common.listener.ListenersInterface.OnClickListener listener) {
        StringBuilder js = new StringBuilder();

        js.append(jsBase).append(".listen('pointClick', function(e) {");

        if (listener.getFields() != null) {
            js.append("var result = ");
            for (String field : listener.getFields()) {
                js.append(String.format(Locale.US, "'%1$s' + ':' + e.point.get('%1$s') + ',' +", field));
            }
            js.setLength(js.length() - 8);
            js.append(";");

            js.append("android.onClick(result);");
        } else {
            js.append("android.onClick(null);");
        }
        js.append("});");

        com.anychart.chart.common.listener.ListenersInterface.getInstance().setOnClickListener(listener);

        APIlib.getInstance().addJSLine(js.toString());
    }
    /**
     * Removes an event listener which was added with listen() by the key returned by listen() or listenOnce().
     */
    public void unlistenByKey(String key) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".unlistenByKey(%s);", wrapQuotes(key)));
    }
    /**
     * Getter for the unselectOnClickOutOfPoint.
     */
    public void unselectOnClickOutOfPoint() {
        APIlib.getInstance().addJSLine(jsBase + ".unselectOnClickOutOfPoint();");
    }
    /**
     * Setter for the unselectOnClickOutOfPoint.<br/>
If the value is <b>true<b/>, disables select all points when clicking outside the chart point.
     */
    public com.anychart.core.utils.MapInteractivity unselectOnClickOutOfPoint(Boolean value) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".unselectOnClickOutOfPoint(%s);", value));

        return this;
    }
    /**
     * Getter for the double click settings.
     */
    public void zoomOnDoubleClick() {
        APIlib.getInstance().addJSLine(jsBase + ".zoomOnDoubleClick();");
    }
    /**
     * Enables double click zoom.
     */
    public com.anychart.core.utils.MapInteractivity zoomOnDoubleClick(Boolean value) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".zoomOnDoubleClick(%s);", value));

        return this;
    }
    /**
     * Getter for the mouse wheel settings.
     */
    public void zoomOnMouseWheel() {
        APIlib.getInstance().addJSLine(jsBase + ".zoomOnMouseWheel();");
    }
    /**
     * Allows use the mouse wheel to zoom.
     */
    public com.anychart.core.utils.MapInteractivity zoomOnMouseWheel(Boolean value) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".zoomOnMouseWheel(%s);", value));

        return this;
    }

}