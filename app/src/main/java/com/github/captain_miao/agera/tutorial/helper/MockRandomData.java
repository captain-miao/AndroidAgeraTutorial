package com.github.captain_miao.agera.tutorial.helper;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.graphics.Color;

import com.github.captain_miao.agera.tutorial.model.BaseObservableVehicleInfo;
import com.github.captain_miao.agera.tutorial.model.ObservableVehicleInfo;
import com.github.captain_miao.agera.tutorial.model.VehicleInfo;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * @author YanLu
 * @since 16/4/26
 */
public class MockRandomData {

    public static String getRandomImage() {
      SecureRandom secureRandom = new SecureRandom();
        return sImages[secureRandom.nextInt(9)];
    }


    public static String getRandomErrorImage() {
        SecureRandom secureRandom = new SecureRandom();
        return sErrorImages[secureRandom.nextInt(9)];
    }


    public static int getRandomColor() {
        SecureRandom secureRandom = new SecureRandom();
        return Color.HSVToColor(150, new float[]{
                secureRandom.nextInt(359), 1, 1
        });
    }

    public static List<VehicleInfo> getVehicleInfos(){
        return sVehicleInfos;
    }
    public static List<ObservableVehicleInfo> getObservableVehicleInfos(){
        ObservableArrayList<ObservableVehicleInfo> vehicleInfos = new ObservableArrayList<>();
        for (VehicleInfo vehicleInfo : sVehicleInfos) {
            vehicleInfos.add(new ObservableVehicleInfo(vehicleInfo));
        }
        return vehicleInfos;
    }
    public static List<BaseObservableVehicleInfo> getBaseObservableVehicleInfos(){
        List<BaseObservableVehicleInfo> vehicleInfos = new ArrayList<>();
        for (VehicleInfo vehicleInfo : sVehicleInfos) {
            vehicleInfos.add(new BaseObservableVehicleInfo(vehicleInfo));
        }
        return vehicleInfos;
    }

    private static List<VehicleInfo> sVehicleInfos = new ArrayList<VehicleInfo>(){{
        add(new VehicleInfo(new ObservableBoolean(true),  "http://www.carlogos.org/uploads/car-logos/Bugatti-logo-1.jpg",
                "Bugatti", "Italian-born Ettore Bugatti showed great interest in engineering and automotive industry since teenage years, worked for several manufacturers and by 1909 had enough experience and enthusiasm to found his own car-manufacturing company Automobiles E.Bugatti."));
        add(new VehicleInfo(new ObservableBoolean(false), "http://www.carlogos.org/uploads/car-logos/Hennessey-logo-1.jpg",
                "Hennessey", "Hennessey Performance Engineering is an American tuning house specializing in modifying sports and super cars from several brands like Ferrari, Porsche, McLaren, Chevrolet, Dodge, Audi, Mercedes-Benz, Toyota, Nissan, Mustang, Cadillac, Lotus, Jeep, Ford, BMW, Bentley, Chrysler, GMC, Lincoln and Lexus."));
        add(new VehicleInfo(new ObservableBoolean(false), "http://www.carlogos.org/uploads/car-logos/Koenigsegg-logo-1.jpg",
                "Koenigsegg", "The story goes that Von Koenigsegg got inspired by the Norwegian animated movie ‘Pinchcliffe Grand Prix’ when he was just a child and ever since he dreamt of building his own supercar – just like the main character in the movie, which is a bicycle repairman, one day builds his own racing car."));
        add(new VehicleInfo(new ObservableBoolean(false), "http://www.carlogos.org/uploads/car-logos/SSC-North-America-logo-1.jpg",
                "SSC Ultimate Aero", "SSC is an American supercar company founded in 1999 by automotive enthusiast, Jerod Shelby. SSC's headquarters in Tri-Cities, Washington, is also the hometown of Mr. Shelby and home to his dream. Fueled by his passion for racing and automotive culture SSC's success is a true representation of the American Dream."));
        add(new VehicleInfo(new ObservableBoolean(false), "http://www.carlogos.org/uploads/car-logos/McLaren-logo-1.jpg",
                "McLaren", "McLaren, also known as McLaren Automotive, it is one of the leading British high-performance automotive manufacturers and has made incredible contributions to the industry. Unlike other British car manufacturers, McLaren has formed several different companies and has changed how we perceive luxury cars in this day and age. Continue reading the McLaren history to see how this brand came into shape and where it stands today."));
        add(new VehicleInfo(new ObservableBoolean(false), "http://www.carlogos.org/uploads/car-logos/Zenvo-logo-1.jpg",
                "Zenvo", "The Zenvo ST1 is a high performance supercar manufactured by Danish company Zenvo. It is the company's first model, and is manufactured almost entirely by the hands of a small team of workers, with the exception of a CNC router."));
        add(new VehicleInfo(new ObservableBoolean(false), "http://www.carlogos.org/uploads/car-logos/Gumpert-logo-1.jpg",
                "Gumpert Apollo", "Gumpert Sportwagenmanufaktur GmbH was founded by former Audi Director Roland Gumpert in 2004. Its car manufacturing company is based in Altenburg, Germany."));
        add(new VehicleInfo(new ObservableBoolean(false), "http://www.carlogos.org/uploads/car-logos/Aston-Martin-logo-3.jpg",
                "Aston Martin", "Founded in 1913 by Robert Bamford and Lionel Martin as 'Bamford & Martin Ltd', the company has developed into an iconic brand synonymous with luxury and elegance. 1914 saw the birth of the name ‘Aston Martin’ following one of Lionel Martin's successful runs at the Aston Hill Climb in Buckinghamshire, England. Within a year the first Aston Martin had been built and registered with the name, and an icon of the automotive world was born."));
        add(new VehicleInfo(new ObservableBoolean(false), "http://www.carlogos.org/uploads/car-logos/Lamborghini-logo-1.jpg",
                "Lamborghini Aventador", "The Lamborghini logo symbolizes the founder’s zodiac character – the Taurus or a bull. Ferruccio’s love of bullfights was depicted in the logo and Lamborghini cars get their styles from famous bulls. The golden bull ready for bullfights is depicted on the black shield with the golden title \"Lamborghini\" above. The bull represents Lamborghini sports cars’ power."));
        add(new VehicleInfo(new ObservableBoolean(false), "http://www.carlogos.org/uploads/car-logos/Ferrari-logo-1.jpg",
                "LaFerrari", "The horse was initially the sign of the famous Count Francesco Baracca. He was a legendary professional of the air force of Italy at the World War I, who depicted it on the wing of his planes. Francesco Baracca died very yearly on June 19 in 1918, shot down later on 34 victorious fightings and many team triumphs."));
    }};

    private static String[] sErrorImages = new String[]{
            "http://ww1.sinaimg.cn/large/7a8aed7bjw1f2sm0ns82hj20f00l8tb9.jpg_error",
            "http://ww4.sinaimg.cn/large/7a8aed7bjw1f2tpr3im0mj20f00l6q4o.jpg",
            "http://ww4.sinaimg.cn/large/610dc034jw1f2uyg3nvq7j20gy0p6myx.jpg_error",
            "http://ww2.sinaimg.cn/large/7a8aed7bjw1f2w0qujoecj20f00kzjtt.jpg",
            "http://ww3.sinaimg.cn/large/7a8aed7bjw1f2x7vxkj0uj20d10mi42r.jpg_error",
            "http://ww1.sinaimg.cn/large/7a8aed7bjw1f2zwrqkmwoj20f00lg0v7.jpg",
            "http://ww2.sinaimg.cn/large/7a8aed7bjw1f30sgi3jf0j20iz0sg40a.jpg_error",
            "http://ww4.sinaimg.cn/large/7a8aed7bjw1f32d0cumhkj20ey0mitbx.jpg",
            "http://ww2.sinaimg.cn/large/610dc034gw1f35cxyferej20dw0i2789.jpg_error",
            "http://ww2.sinaimg.cn/large/7a8aed7bjw1f340c8jrk4j20j60srgpf.jpg"
    };

    private static String[] sImages = new String[]{
            "http://ww1.sinaimg.cn/large/7a8aed7bjw1f2sm0ns82hj20f00l8tb9.jpg",
            "http://ww4.sinaimg.cn/large/7a8aed7bjw1f2tpr3im0mj20f00l6q4o.jpg",
            "http://ww4.sinaimg.cn/large/610dc034jw1f2uyg3nvq7j20gy0p6myx.jpg",
            "http://ww2.sinaimg.cn/large/7a8aed7bjw1f2w0qujoecj20f00kzjtt.jpg",
            "http://ww3.sinaimg.cn/large/7a8aed7bjw1f2x7vxkj0uj20d10mi42r.jpg",
            "http://ww1.sinaimg.cn/large/7a8aed7bjw1f2zwrqkmwoj20f00lg0v7.jpg",
            "http://ww2.sinaimg.cn/large/7a8aed7bjw1f30sgi3jf0j20iz0sg40a.jpg",
            "http://ww4.sinaimg.cn/large/7a8aed7bjw1f32d0cumhkj20ey0mitbx.jpg",
            "http://ww2.sinaimg.cn/large/610dc034gw1f35cxyferej20dw0i2789.jpg",
            "http://ww2.sinaimg.cn/large/7a8aed7bjw1f340c8jrk4j20j60srgpf.jpg"
    };
}
