package quepierts.iter.client.resource;

public enum EnumResourceType {
    model(".obj"),
    texture(".png"),
    shader(".vsh", ".fsh"),
    audio(".ogg");

    private String[] fileType;

    private EnumResourceType(String... fileType) {
        this.fileType = fileType;
    }

    public String getFileType(int index) {
        if (index > fileType.length) {
            return fileType[0];
        }
        return fileType[index];
    }

}
