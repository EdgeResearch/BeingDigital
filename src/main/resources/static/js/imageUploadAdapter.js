class ImageUploadAdapter {
    constructor(loader) {
        // Save Loader instance to use later
        this.loader = loader;
    }

    // Implement the uploading logic
    upload() {
        return this.loader.file
            .then(file => new Promise((resolve, reject) => {
                // Read the file as data URL
                const reader = new FileReader();

                reader.onload = (event) => {
                    resolve({default: event.target.result});
                };

                reader.readAsDataURL(file);
            }));
    }
}

// Function to enable the image upload adapter
function MyCustomUploadAdapterPlugin(editor) {
    editor.plugins.get('FileRepository').createUploadAdapter = (loader) => {
        return new ImageUploadAdapter(loader);
    };
}