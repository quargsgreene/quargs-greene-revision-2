package com.example.stacker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

public class BlockView extends View {
    private static final int BLOCK_SIZE = 100;
    private static final int BLOCK_GAP = 10;
    private static final int DELAY_MILLIS = 50;

    private Paint paint;
    private List<int[]> rows;
    private int[] blockDirections;
    private int numBlocks = 3;
    private Handler handler;
    private boolean blocksMoving;
    private int[] previousRow;
    private EndGameListener endGameListener;

    public interface EndGameListener {
        void onGameEnded();
    }

    public BlockView(Context context, EndGameListener listener) {
        super(context);
        this.endGameListener = listener;
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.RED);
        rows = new ArrayList<>();
        handler = new Handler(Looper.getMainLooper());
        blockDirections = new int[numBlocks];

        int[] initialRow = new int[numBlocks];
        for (int i = 0; i < numBlocks; i++) {
            initialRow[i] = i * (BLOCK_SIZE + BLOCK_GAP);
            blockDirections[i] = 1;
        }
        rows.add(initialRow);

        blocksMoving = true;
        postMoveBlocks();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int[] row : rows) {
            for (int i = 0; i < row.length; i++) {
                canvas.drawRect(
                        row[i],
                        getHeight() - BLOCK_SIZE - (BLOCK_SIZE + BLOCK_GAP) * rows.indexOf(row),
                        row[i] + BLOCK_SIZE,
                        getHeight() - (BLOCK_SIZE + BLOCK_GAP) * rows.indexOf(row),
                        paint
                );
            }
        }
    }

    private void postMoveBlocks() {
        handler.postDelayed(this::moveBlocks, DELAY_MILLIS);
    }

    private void moveBlocks() {
        if (!blocksMoving) return;
        int[] currentRow = rows.get(rows.size() - 1);
        for (int i = 0; i < numBlocks; i++) {
            currentRow[i] += blockDirections[i] * (BLOCK_SIZE + BLOCK_GAP);
            if (currentRow[i] >= getWidth() - BLOCK_SIZE || currentRow[i] <= 0) {
                blockDirections[i] = -blockDirections[i];
            }
        }
        invalidate();
        postMoveBlocks();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN && blocksMoving) {
            stopBlocks();
            checkAlignmentAndAddRow();
            if (numBlocks == 1) {
                endGame();
            } else {
                postMoveBlocks();
            }
            performClick();
        }
        return true;
    }

    private void stopBlocks() {
        blocksMoving = false;
        handler.removeCallbacksAndMessages(null);
    }

    private void checkAlignmentAndAddRow() {
        int[] currentRow = rows.get(rows.size() - 1).clone();
        if (rows.size() > 1) {
            previousRow = rows.get(rows.size() - 2);
            boolean isAligned = true;
            for (int i = 0; i < numBlocks; i++) {
                if (i >= previousRow.length || Math.abs(currentRow[i] - previousRow[i]) > BLOCK_GAP) {
                    isAligned = false;
                    break;
                }
            }
            if (!isAligned && numBlocks > 1) {
                numBlocks--;
            } else if (!isAligned && numBlocks == 1) {
                endGame();
                return;
            }
        }

        int[] nextRow = new int[numBlocks];
        System.arraycopy(currentRow, 0, nextRow, 0, numBlocks);
        rows.add(nextRow);
        blockDirections = new int[numBlocks];
        for (int i = 0; i < numBlocks; i++) {
            blockDirections[i] = 1;
        }
        blocksMoving = true;
    }

    private void endGame() {
        if (endGameListener != null) {
            endGameListener.onGameEnded();
        }
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }
}
