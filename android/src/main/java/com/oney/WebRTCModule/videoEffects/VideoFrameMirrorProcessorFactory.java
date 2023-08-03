package com.oney.WebRTCModule.videoEffects;

import org.webrtc.SurfaceTextureHelper;
import org.webrtc.VideoFrame;

public class VideoFrameMirrorProcessorFactory implements VideoFrameProcessorFactoryInterface {
  @Override
  public VideoFrameProcessor build() {
    return new VideoFrameMirrorProcessor();
  }
}
