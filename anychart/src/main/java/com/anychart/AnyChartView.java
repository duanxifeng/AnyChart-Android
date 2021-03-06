package com.anychart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.anychart.chart.common.listener.ListenersInterface;
import com.anychart.core.Chart;

public final class AnyChartView extends FrameLayout {

    public interface JsListener {
        void onJsLineAdd(String jsLine);
    }

    private JsListener listener;

    private WebView webView;
    private Chart chart;

    private boolean isRestored;
    private boolean isRendered;

    private StringBuilder scripts = new StringBuilder();
    private StringBuilder fonts = new StringBuilder();

    protected StringBuilder js = new StringBuilder();

    private String licenceKey = "";
    private View progressBar;
    private String backgroundColor;

    public AnyChartView(Context context) {
        super(context);
        init();
    }

    public AnyChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AnyChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("superState", super.onSaveInstanceState());
        bundle.putString("js", this.js.toString());

        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            this.js.append(bundle.getString("js"));
            state = bundle.getParcelable("superState");
        }
        isRestored = true;

        super.onRestoreInstanceState(state);
    }

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_anychart, this, true);

        APIlib.getInstance().setActiveAnyChartView(this);

        if (progressBar != null)
            progressBar.setVisibility(VISIBLE);

        webView = view.findViewById(R.id.web_view);
        WebSettings webSettings = webView.getSettings();
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webView.setLongClickable(true);
        webView.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                webView.setEnabled(false);
                Log.wtf("TEST", consoleMessage.message());
                return true;
            }
        });

        isRendered = false;
        JsObject.variableIndex = 0;
        setJsListener(new JsListener() {
            @Override
            public void onJsLineAdd(final String jsLine) {
                webView.post(new Runnable() {
                    @Override
                    public void run() {
                        if (isRendered) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                webView.evaluateJavascript(jsLine, null);
                            } else {
                                webView.loadUrl("javascript:" + jsLine);
                            }
                        } else {
                            js.append(jsLine);
                        }
                    }
                });
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                if (chart != null) {
                    if (!isRestored) { //!isRendered &&
//                        js.append(chart.generateJs());
                    }
                } else {
                    throw new NullPointerException();
                }

                Log.wtf("TEST", js.toString());

                String resultJs = (isRestored)
                        ? js.toString()
                        : js.append(chart.getJsBase()).append(".container(\"container\");")
                        .append(chart.getJsBase()).append(".draw();")
                        .toString();

                webView.evaluateJavascript(
                        "anychart.licenseKey(\"" + licenceKey + "\");" +
                                "anychart.onDocumentReady(function () {\n" +
                                resultJs +
                                "});",
                        new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String value) {
                                if (progressBar != null)
                                    progressBar.setVisibility(GONE);
                            }
                        });

                isRendered = true;

//                chart.setOnChangeListener(new Chart.OnChange() {
//                    @Override
//                    public void onChange(final String jsChange) {
//                        js.append(jsChange);
//                        webView.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                webView.evaluateJavascript(jsChange, null);
//                            }
//                        });
//                    }
//                });
            }
        });
        webView.addJavascriptInterface(ListenersInterface.getInstance(), "android");
    }

    private void loadHtml() {
        String htmlData = "<html>\n" +
                "<head>\n" +
                "    <meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">\n" +
                "    <style type=\"text/css\">\n" +
                "        html, body, #container {\n" +
                "            width: 100%;\n" +
                "            height: 100%;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                ((backgroundColor != null) ? "background-color: " + backgroundColor + ";" : "") +
                "        }\n" +
                fonts.toString() +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<script src=\"file:///android_asset/anychart-bundle.min.js\"></script>" +
                scripts.toString() +
                "<link rel=\"stylesheet\" href=\"file:///android_asset/anychart-ui.min.css\"/>\n" +
                "<div id=\"container\"></div>\n" +
                "</body>\n" +
                "</html>";

        webView.loadDataWithBaseURL("", htmlData, "text/html", "UTF-8", null);
    }

    public void addScript(String url) {
        scripts.append("<script src=\"")
                .append(url)
                .append("\"></script>\n");
    }

    public void addCss(String url) {
        scripts.append("<link rel=\"stylesheet\" href=\"")
                .append(url)
                .append("\"/>\n");
    }

    public void addFont(String fontFamily, String url) {
        fonts.append("@font-face {\n")
                .append("font-family: ").append(fontFamily).append(";\n")
                .append("src: url(").append(url).append(");\n")
                .append("}\n");
    }

    public void setLicenceKey(String key) {
        licenceKey = key;
    }

    public void setZoomEnabled(Boolean value) {
        webView.getSettings().setBuiltInZoomControls(value);
        webView.getSettings().setDisplayZoomControls(!value);
    }

    public void clear() {
        webView.loadUrl("about:blank");
        isRendered = false;
        if (progressBar != null) {
            progressBar.setVisibility(VISIBLE);
        }
    }

    public void setChart(Chart chart) {
        js.setLength(0);
        isRestored = false;
        this.chart = chart;
        loadHtml();
    }

    public void setProgressBar(View progressBar) {
        this.progressBar = progressBar;
        progressBar.setVisibility(VISIBLE);
    }

    public void setBackgroundColor(String color) {
        this.backgroundColor = color;
        webView.setBackgroundColor(Color.parseColor(color));
    }

    public void setJsListener(JsListener listener) {
        this.listener = listener;
    }

    public JsListener getJsListener() {
        return listener;
    }

}
