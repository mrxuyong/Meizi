package com.spark.meizi.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.spark.meizi.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment implements RequestListener<String, GlideDrawable> {

    private String url;
    @Bind(R.id.iv_meizi)
    ImageView image;
    //Photoview can't work correctly in Transition
    ImageView bugImage;
    PhotoViewAttacher attacher;

    public static DetailFragment newInstance(String url) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString("url", url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            url = getArguments().getString("url");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        getActivity().supportStartPostponedEnterTransition();
        bugImage = new ImageView(getActivity());

    }

    @Override
    public void onResume() {
        super.onResume();
        Glide.with(this)
                .load(url)
                .listener(this)
                .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
    }

    public ImageView getImage() {
        return bugImage;
    }

    @Override
    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
        getActivity().supportStartPostponedEnterTransition();
        return false;
    }

    @Override
    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
        bugImage.setImageDrawable(resource);
        image.setImageDrawable(resource);
//        image.setImageDrawable(bugImage.getDrawable());
        attacher = new PhotoViewAttacher(image);
        return true;
    }
}
