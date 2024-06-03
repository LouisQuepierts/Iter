package quepierts.iter.client.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResourceID implements Comparable<ResourceID> {
    public final EnumResourceType type;
    public final String name;

    public static final Map<EnumResourceType, List<ResourceID>> resources = new HashMap<>();

    public static ResourceID get(EnumResourceType type, String id) {
        List<ResourceID> resourceIDS = resources.get(type);

        for (ResourceID resourceID : resourceIDS) {
            if (id.equals(resourceID.name)) {
                return resourceID;
            }
        }

        ResourceID resourceID = new ResourceID(type, id);
        resourceIDS.add(resourceID);

        return resourceID;
    }


    public static void init() {
        for (EnumResourceType value : EnumResourceType.values()) {
            resources.put(value, new ArrayList<>());
        }
    }

    private ResourceID(EnumResourceType type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getResourcePath(int subType) {
        StringBuilder builder = new StringBuilder("assets/")
                .append(type.name().toLowerCase())
                .append("/")
                .append(name)
                .append(type.getFileType(subType));

        return builder.toString();
    }

    @Override
    public int compareTo(ResourceID o) {
        int i = type.compareTo(o.type);
        if (i == 0) {
            i = name.compareTo(o.name);
        }
        return i;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof String) {
            return name.equals(obj);
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getResourcePath(0);
    }
}
