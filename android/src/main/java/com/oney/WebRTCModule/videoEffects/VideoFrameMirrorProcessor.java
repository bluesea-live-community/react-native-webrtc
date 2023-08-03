package com.oney.WebRTCModule.videoEffects;

import android.util.Log;

import org.webrtc.SurfaceTextureHelper;
import org.webrtc.VideoFrame;
import org.webrtc.VideoFrame.Buffer;
import org.webrtc.VideoFrame.I420Buffer;

import java.nio.ByteBuffer;

public class VideoFrameMirrorProcessor implements VideoFrameProcessor {
  @Override
  public VideoFrame process(VideoFrame frame, SurfaceTextureHelper textureHelper) {
    I420Buffer i420Buffer = frame.getBuffer().toI420();
    if (i420Buffer == null) {
      return null;
    }
    int width = i420Buffer.getWidth();
    int height = i420Buffer.getHeight();
    int strideY = i420Buffer.getStrideY();
    int strideU = i420Buffer.getStrideU();
    int strideV = i420Buffer.getStrideV();
    ByteBuffer dataY = i420Buffer.getDataY();
    ByteBuffer dataU = i420Buffer.getDataU();
    ByteBuffer dataV = i420Buffer.getDataV();
    for (int row = 0; row < height / 2; row++) {
      int row_inv = height - row - 1;
      for (int col = 0; col < width; col ++) {
        this.swapBuffer(dataY, row, row_inv, col, strideY);
        if (row % 2 == 0 && col % 2 == 0) {
          this.swapBuffer(dataU, row/2, row_inv/2, col/2, strideU);
          this.swapBuffer(dataV, row/2, row_inv/2, col/2, strideV);
        }
      }
    }
    return new VideoFrame(i420Buffer, frame.getRotation(), frame.getTimestampNs());
  }

  private void swapBuffer(ByteBuffer buffer, int row1, int row2, int col, int stride) {
    byte tmp = buffer.get(row1 * stride + col);
    buffer.put(row1 * stride + col, buffer.get(row2 * stride + col));
    buffer.put(row2 * stride + col, tmp);
  }
}
