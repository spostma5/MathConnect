/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://code.google.com/p/google-apis-client-generator/
 * (build: 2015-01-14 17:53:03 UTC)
 * on 2015-03-03 at 18:43:49 UTC 
 * Modify at your own risk.
 */

package com.example.kyoukasuigetsu.mathconnect.backend.myApi.model;

/**
 * Model definition for MyRoom.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the myApi. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class MyRoom extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String colour;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String drawing;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String friends;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String path;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String room;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String roomAll;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String size;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getColour() {
    return colour;
  }

  /**
   * @param colour colour or {@code null} for none
   */
  public MyRoom setColour(java.lang.String colour) {
    this.colour = colour;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getDrawing() {
    return drawing;
  }

  /**
   * @param drawing drawing or {@code null} for none
   */
  public MyRoom setDrawing(java.lang.String drawing) {
    this.drawing = drawing;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getFriends() {
    return friends;
  }

  /**
   * @param friends friends or {@code null} for none
   */
  public MyRoom setFriends(java.lang.String friends) {
    this.friends = friends;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getPath() {
    return path;
  }

  /**
   * @param path path or {@code null} for none
   */
  public MyRoom setPath(java.lang.String path) {
    this.path = path;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getRoom() {
    return room;
  }

  /**
   * @param room room or {@code null} for none
   */
  public MyRoom setRoom(java.lang.String room) {
    this.room = room;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getRoomAll() {
    return roomAll;
  }

  /**
   * @param roomAll roomAll or {@code null} for none
   */
  public MyRoom setRoomAll(java.lang.String roomAll) {
    this.roomAll = roomAll;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getSize() {
    return size;
  }

  /**
   * @param size size or {@code null} for none
   */
  public MyRoom setSize(java.lang.String size) {
    this.size = size;
    return this;
  }

  @Override
  public MyRoom set(String fieldName, Object value) {
    return (MyRoom) super.set(fieldName, value);
  }

  @Override
  public MyRoom clone() {
    return (MyRoom) super.clone();
  }

}
