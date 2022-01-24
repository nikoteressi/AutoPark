package vehiclecreation;

import java.util.List;

public interface Loader<T> {
    List<T> load(String path);
}
