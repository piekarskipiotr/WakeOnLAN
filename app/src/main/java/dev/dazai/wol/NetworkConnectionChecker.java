package dev.dazai.wol;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import androidx.annotation.NonNull;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import dev.dazai.wol.databinding.NoInternetConnectionDailogBinding;

public class NetworkConnectionChecker {
    private final BottomSheetDialog mBottomSheetDialog;
    private final ConnectivityManager connectivityManager;
    private final ConnectivityManager.NetworkCallback connectivityCallback;

    public NetworkConnectionChecker(Context context, NoInternetConnectionDailogBinding binding, BottomSheetDialog bottomSheetDialog){
        mBottomSheetDialog = bottomSheetDialog;
        mBottomSheetDialog.setContentView(binding.getRoot());
        mBottomSheetDialog.setCancelable(false);
        mBottomSheetDialog.setCanceledOnTouchOutside(false);
        NetworkRequest request = new NetworkRequest.Builder().build();
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        connectivityCallback = new ConnectivityManager.NetworkCallback(){

            @Override
            public void onAvailable(@NonNull Network network) {
                mBottomSheetDialog.hide();
            }

            @Override
            public void onLost(@NonNull Network network) {
                mBottomSheetDialog.show();
            }
        };

        connectivityManager.registerNetworkCallback(request, connectivityCallback);
    }

    public void unRegister(){
        connectivityManager.unregisterNetworkCallback(connectivityCallback);
    }

}
