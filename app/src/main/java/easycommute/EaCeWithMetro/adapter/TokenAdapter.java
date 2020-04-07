package easycommute.EaCeWithMetro.adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import easycommute.EaCeWithMetro.R;
import easycommute.EaCeWithMetro.models.MyTokens.TokenResponse;

public class TokenAdapter extends RecyclerView.Adapter<TokenAdapter.ViewHolder> {

    Bitmap bitmap ;
    private TokenResponse tokenResponse;
    public final static int QRcodeWidth = 350 ;
    Context context;

    public TokenAdapter(TokenResponse tokenResponse, FragmentActivity context)
    {
        this.tokenResponse = tokenResponse;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.token_list, parent, false);

        ViewHolder viewHolder = new ViewHolder(view, viewType);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,final int position)
    {
        String token = tokenResponse.getTokenHistory().get(position).display_tkt_msg;
        holder.tokenText.setText(token);

        holder.tokenText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                try
                {
                    bitmap = TextToImageEncode(tokenResponse.getTokenHistory().get(position).token);
                    showDialog(bitmap);
                    //imageView.setImageBitmap(bitmap);
                }
                catch (WriterException e)
                {
                    e.printStackTrace();
                }

            }
        });

    }

    public void showDialog(Bitmap btmp)
    {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog);
        ImageView imgBarcode = (ImageView) dialog.findViewById(R.id.imgBarcode);
        imgBarcode.setImageBitmap(btmp);
        dialog.show();
    }


    Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                  BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        context.getResources().getColor(R.color.black):context.getResources().getColor(R.color.white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 350, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }

    @Override
    public int getItemCount() {
        return tokenResponse.getTokenHistory().size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position)
    {
        return position;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView tokenText;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);
             tokenText = (TextView) itemView.findViewById(R.id.token_text);
        }
    }
}
