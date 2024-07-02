package com.ashrafunahid.carouselsliderauto;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewbinding.ViewBinding;

import com.ashrafunahid.carouselsliderauto.databinding.ActivityMainBinding;
import com.ashrafunahid.carouselsliderauto.databinding.ItemCustomFixedSizeLayout1Binding;
import com.ashrafunahid.carouselsliderauto.databinding.ItemCustomFixedSizeLayout3Binding;

import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.listener.CarouselListener;
import org.imaginativeworld.whynotimagecarousel.model.CarouselGravity;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;
import org.imaginativeworld.whynotimagecarousel.model.CarouselType;
import org.imaginativeworld.whynotimagecarousel.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    ImageCarousel carousel;
    List<CarouselItem> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        carousel = findViewById(R.id.carousel);
        carousel.registerLifecycle(getLifecycle());

        list = new ArrayList<>();

//        // Image URL with caption
//        list.add(new CarouselItem("https://images.unsplash.com/photo-1532581291347-9c39cf10a73c?w=1080", "Photo by Aaron Wu on Unsplash"));
//        // Just image URL
//        list.add(new CarouselItem("https://images.unsplash.com/photo-1534447677768-be436bb09401?w=1080"));
//        // Image URL with header
//        Map<String, String> headers = new HashMap<>();
//        headers.put("header_key", "header_value");
//        list.add(new CarouselItem("https://images.unsplash.com/photo-1534447677768-be436bb09401?w=1080", headers));

        list.add(new CarouselItem(R.drawable.bill_gates, "Carousel slicer"));
        list.add(new CarouselItem(R.drawable.bill_gates_2));
        list.add(new CarouselItem(R.drawable.bill_gates_3));
        list.add(new CarouselItem(R.drawable.bill_gates_4, "Carousel Auto Slider"));
        list.add(new CarouselItem(R.drawable.bill_gates_5));
        list.add(new CarouselItem(R.drawable.bill_gates_6));

        carousel.setShowIndicator(false);
        carousel.setShowNavigationButtons(false);
        carousel.setShowTopShadow(false);
        carousel.setShowBottomShadow(false);
        carousel.setPadding(20, 10,20,10);
        carousel.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
        carousel.setCarouselBackground(new ColorDrawable(Color.TRANSPARENT));
        carousel.setAutoWidthFixing(true);
        carousel.setCarouselType(CarouselType.SHOWCASE);
        carousel.setScaleOnScroll(true);
        carousel.setScalingFactor(.25f);
        carousel.setCarouselGravity(CarouselGravity.CENTER);
        carousel.setAutoPlay(true);
        carousel.setAutoPlayDelay(3000); // Milliseconds
        carousel.setTouchToPause(true);
        carousel.setInfiniteCarousel(true);
        carousel.setCarouselListener(new CarouselListener() {
            @Nullable
            @Override
            public ViewBinding onCreateViewHolder(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup) {
                return ItemCustomFixedSizeLayout3Binding.inflate(layoutInflater, viewGroup, false);
            }

            @Override
            public void onBindViewHolder(@NonNull ViewBinding viewBinding, @NonNull CarouselItem carouselItem, int i) {
                // Cast the binding to the returned view binding class of the onCreateViewHolder() method.
                ItemCustomFixedSizeLayout3Binding currentBinding = (ItemCustomFixedSizeLayout3Binding) viewBinding;

                // Do the bindings...
                currentBinding.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

                // setImage() is an extension function to load image to an ImageView using CarouselItem object. We need to provide current CarouselItem data and the place holder Drawable or drawable resource id to the function. placeholder parameter is optional.
                Utils.setImage(currentBinding.imageView, carouselItem, R.drawable.ic_wb_cloudy_with_padding);
            }

            @Override
            public void onClick(int i, @NonNull CarouselItem carouselItem) {
                Toast.makeText(MainActivity.this, "Carousel Item Number: " + i, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(int i, @NonNull CarouselItem carouselItem) {

            }
        });
        carousel.setElevation(10);
        carousel.setData(list);
        carousel.start();

    }
}