package com.lifehackinnovations.wallet;





import android.graphics.Canvas;
import android.view.SurfaceHolder;



class CardViewThread extends Thread {
        private SurfaceHolder _surfaceHolder;
        private CardView.cPanel _panel;
        private boolean _run = false;
 
        public CardViewThread(SurfaceHolder surfaceHolder, CardView.cPanel panel) {
            _surfaceHolder = surfaceHolder;
            _panel = panel;
        }
 
        public void setRunning(boolean run) {
            _run = run;
        }
 
        @Override
        public void run() {
            Canvas c;
            while (_run) {
                c = null;
                try {
                    c = _surfaceHolder.lockCanvas(null);
                    synchronized (_surfaceHolder) {
                        _panel.onDraw(c);
                    }
                } finally {
                    // do this in a finally so that if an exception is thrown
                    // during the above, we don't leave the Surface in an
                    // inconsistent state
                    if (c != null) {
                        _surfaceHolder.unlockCanvasAndPost(c);
                    }
                }
            }
        }
    }