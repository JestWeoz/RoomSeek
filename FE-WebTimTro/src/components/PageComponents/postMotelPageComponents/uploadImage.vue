<template>
  <div class="image-upload-container">
    <div
      class="upload-area"
      @dragover.prevent="isDragging = true"
      @dragleave.prevent="isDragging = false"
      @drop.prevent="handleDrop"
      :class="{ 'drag-over': isDragging }"
    >
      <input
        type="file"
        ref="fileInput"
        multiple
        accept="image/*"
        @change="handleFileSelect"
        class="file-input"
      />
      <div class="upload-content">
        <img src="/img/cloudUpload.svg" alt="Upload Icon" class="upload-icon" />
        <p class="upload-text">Kéo thả hình ảnh vào đây</p>
        <p class="upload-text-sub">hoặc</p>
        <button class="upload-button" @click="triggerFileInput">
          Chọn hình ảnh
        </button>
        <p class="file-limit">Tối đa 10 hình ảnh (*.jpg, *.png)</p>
      </div>

      <!-- Preview Images -->
      <div class="image-preview" v-if="previewImages.length">
        <div
          v-for="(image, index) in previewImages"
          :key="index"
          class="preview-item"
        >
          <img :src="image" class="preview-image" />
          <button class="remove-button" @click="removeImage(index)">×</button>
        </div>
      </div>
    </div>
  </div>
</template>
  
  <script>
export default {
  name: "uploadImage",
  data() {
    return {
      isDragging: false,
      previewImages: [],
      uploadedFiles: [],
    };
  },
  methods: {
    triggerFileInput() {
      this.$refs.fileInput.click();
    },
    handleDrop(e) {
      const files = e.dataTransfer.files;
      this.processFiles(files);
      this.isDragging = false;
    },
    handleFileSelect(e) {
      const files = e.target.files;
      this.processFiles(files);
    },
    processFiles(files) {
      const remainingSlots = 10 - this.previewImages.length;
      const filesToProcess = Array.from(files).slice(0, remainingSlots);

      filesToProcess.forEach((file) => {
        if (file.type.startsWith("image/")) {
          const reader = new FileReader();
          reader.onload = (e) => {
            this.previewImages.push(e.target.result);
            this.uploadedFiles.push(file);
            this.$emit("update:files", this.uploadedFiles); // Phát danh sách file đã chọn
          };
          reader.readAsDataURL(file);
        }
      });
    },
    removeImage(index) {
      this.previewImages.splice(index, 1);
      this.uploadedFiles.splice(index, 1);
      this.$emit("update:files", this.uploadedFiles); // Cập nhật sau khi xóa
    },
  },
};
</script>
  
  <style scoped>
.image-upload-container {
  margin: 20px 0;
}

.upload-area {
  border: 2px dashed #ccc;
  border-radius: 8px;
  padding: 20px;
  text-align: center;
  position: relative;
  min-height: 200px;
  background-color: #f8f9fa;
  transition: all 0.3s ease;
}

.drag-over {
  border-color: #0056b3;
  background-color: #e7f1ff;
}

.file-input {
  display: none;
}

.upload-content {
  padding: 20px;
}

.upload-icon {
  font-size: 48px;
  color: #0056b3;
  margin-bottom: 10px;
}

.upload-text {
  font-size: 18px;
  color: #333;
  margin-bottom: 5px;
}

.upload-text-sub {
  color: #666;
  margin: 10px 0;
}

.upload-button {
  background-color: #0056b3;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.upload-button:hover {
  background-color: #003d82;
}

.file-limit {
  color: #666;
  font-size: 12px;
  margin-top: 10px;
}

.image-preview {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  margin-top: 20px;
}

.preview-item {
  position: relative;
  width: 100px;
  height: 100px;
}

.preview-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 4px;
}

.remove-button {
  position: absolute;
  top: -8px;
  right: -8px;
  background-color: #ff4444;
  color: white;
  border: none;
  border-radius: 50%;
  width: 20px;
  height: 20px;
  line-height: 20px;
  text-align: center;
  cursor: pointer;
  padding: 0;
}

.remove-button:hover {
  background-color: #cc0000;
}
</style>