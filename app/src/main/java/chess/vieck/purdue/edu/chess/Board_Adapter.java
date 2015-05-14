package chess.vieck.purdue.edu.chess;

import android.content.ClipData;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.*;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * Created by Michael on 4/10/2015.
 */
public class Board_Adapter extends BaseAdapter {
    boolean pieceSelected;
    boolean reset = false;
    private Context context;
   // Piece currentPiece = null;

    private int fromX, fromY, toX, toY, topCorner, width;
    FrameLayout touchLayout;
    ImageView touchImage;
    private Logic_Core logic;
    private Resources resources;
    private Canvas canvas;
    private Integer[] boardSquares = new Integer[64];

    Board_Adapter(Context context) {
        this.context = context;
        for (int i = 0; i < 64; i++) {
            if ((i % 2) == 0) {
                boardSquares[i] = R.drawable.blacksqr;
            } else {
                boardSquares[i] = R.drawable.whitesqr;
            }
        }
        pieceSelected = false;
        reset = true;
        fromX = -1;
        fromY = -1;
        toX = -1;
        toY = -1;
        //resources = getResources();

        //setFocusable(true);
        //setFocusableInTouchMode(true);
    }

    protected Integer squareImage(int position) {
        return boardSquares[position];
    }


    public void setLogicEngine(Logic_Core logic) {
        if (logic != null) {
            this.logic = logic;
        }
    }

    private int getBoardXCoor(int x) {
        return x * width / 8;
    }

    private int getBoardYCoor(int y) {
        return y * width / 8 + topCorner;
    }

    /*private void drawBoard(Logic_Core.cell[][] board) {
        //super.invalidate();
        Drawable boardImg = resources.getDrawable(R.drawable.board);
        width = canvas.getWidth();
        topCorner = (int) (canvas.getHeight() - width) / 2;
        boardImg.setBounds(0, topCorner, width, width + topCorner);
        boardImg.draw(canvas);
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Logic_Core.piece piece = board[x][y].getPiece();
                if (piece != null) {
                    Drawable figure = resources.getDrawable(piece.getImageResource());
                    figure.setBounds(getBoardXCoor(x), getBoardYCoor(y), getBoardXCoor(x) + width / 8, getBoardYCoor(y) + width / 8);
                    figure.draw(canvas);
                }
            }
        }
    }*/

    /*private void drawAvailableMoves(cell[][] board, int x, int y) {
        piece piece = board[x][y].getPiece();
        if (piece != null && piece.getPieceColour() == logic.getTurn()) {
            Log.d("DEBUG", "Piece selected");
            Drawable selection = resources.getDrawable(R.drawable.selected);
            selection.setBounds(getBoardXCoor(x), getBoardYCoor(y), getBoardXCoor(x) + width / 8, getBoardYCoor(y) + width / 8);
            selection.draw(canvas);

            ArrayList<cell> availMoves = piece.getAvailableMoves();
            for (int i = 0; i < availMoves.size(); i++) {
                if (piece.isValidMove(availMoves.get(i))) {
                    cell availMove = availMoves.get(i);
                    Drawable circle = resources.getDrawable(R.drawable.selectioncircle);
                    circle.setBounds(getBoardXCoor(availMove.getX()), getBoardYCoor(availMove.getY()), getBoardXCoor(availMove.getX()) + width / 8, getBoardYCoor(availMove.getY()) + width / 8);
                    circle.draw(canvas);
                }
            }
        }
    }*/

    // Draw Chess board and set up logic connections
    // TODO: probably not here. Set up to and from cell selection
    // this may be related to view available moves.
    /*@Override
    protected void onDraw(Canvas canvas) {
        this.canvas = canvas;
        this.drawBoard(logic.getBoard());
        if (pieceSelected)
            drawAvailableMoves(logic.getBoard(), fromX, fromY);
    }
    */

    @Override
    public int getCount() {
        return boardSquares.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View squareContainerView = convertView;

        if (convertView == null) {
            //Inflate the layout
            final LayoutInflater layoutInflater =
                    LayoutInflater.from(this.context);
            squareContainerView =
                    layoutInflater.inflate(R.layout.square, null);

            // Background
            final ImageView squareView =
                    (ImageView) squareContainerView.findViewById(R.id.square_background);
            squareView.setImageResource(this.squareImage((position + position / 8) % 2));

            // Add The piece
            final ImageView pieceView =
                    (ImageView) squareContainerView.findViewById(R.id.piece);
            if (reset) {
                if ((position >= 47 && position <= 55)) {
                    pieceView.setImageResource(R.drawable.blackpawn);
                    pieceView.setTag(position);
                } else if (position >= 8 && position <= 15) {
                    pieceView.setImageResource(R.drawable.whitepawn);
                    pieceView.setTag(position);
                } else if (position == 0 || position == 7) {
                    pieceView.setImageResource(R.drawable.whiterook);
                } else if (position == 56 || position == 63) {
                    pieceView.setImageResource(R.drawable.blackrook);
                } else if (position == 1 || position == 6) {
                    pieceView.setImageResource(R.drawable.whitehorse);
                } else if (position == 57 || position == 62) {
                    pieceView.setImageResource(R.drawable.blackknight);
                } else if (position == 2 || position == 5) {
                    pieceView.setImageResource(R.drawable.whitebishop);
                } else if (position == 58 || position == 61) {
                    pieceView.setImageResource(R.drawable.blackbishop);
                } else if (position == 3) {
                    pieceView.setImageResource(R.drawable.whitequeen);
                } else if (position == 59) {
                    pieceView.setImageResource(R.drawable.blackqueen);
                } else if (position == 4) {
                    pieceView.setImageResource(R.drawable.whiteking);
                } else if (position == 60) {
                    pieceView.setImageResource(R.drawable.blackking);
                }
            }
        }
        return squareContainerView;
    }

    private final class ListenForTouch implements OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN){
                ClipData clipData = ClipData.newPlainText("", "");
                DragShadowBuilder shadowBuilder = new DragShadowBuilder(v);
                v.startDrag(clipData, shadowBuilder,v, 0);
                v.setVisibility(View.INVISIBLE);
                touchLayout = (FrameLayout) v.getParent();
                touchImage = (ImageView) touchLayout.getChildAt(1);

                return true;
            } else {
                return false;
            }
        }
    }

    private final class ListenForDrag implements OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            if (action == DragEvent.ACTION_DROP) {
                FrameLayout square = (FrameLayout) v.getParent();
                ImageView imageView = (ImageView) square.getChildAt(1);
            }
            return true;
        }
    }
}