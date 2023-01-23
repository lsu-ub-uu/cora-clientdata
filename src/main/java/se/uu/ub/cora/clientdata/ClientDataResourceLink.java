/*
 * Copyright 2019 Uppsala University Library
 * Copyright 2022 Olov McKie
 *
 * This file is part of Cora.
 *
 *     Cora is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Cora is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Cora.  If not, see <http://www.gnu.org/licenses/>.
 */
package se.uu.ub.cora.clientdata;

/**
 * DataResourceLink contains information linking the {@link ClientDataRecord} this link is a part of to a
 * resource such as an image. Currently are DataResourceLinks only used in record type binary or
 * children of binary.
 * <p>
 * RecordTypes other than binary, links to a record with the type binary which in turn contains
 * metainformation about the binary file, and can through ResourceLinks point to different versions
 * of the binary data. The different versions can for instance be a thumbnail, a scaled version or
 * the master version of an image.
 */
public interface ClientDataResourceLink extends ClientDataLink, ClientConvertible {
	/**
	 * setStreamId sets the streamId for this link.
	 * <p>
	 * If there is a streamId since before must it be replaced by this method so that only one
	 * exist.
	 */
	void setStreamId(String streamId);

	/**
	 * getStreamId returns the streamId for this link.
	 * <p>
	 * This information is expected to be present, if this link does not have information about what
	 * the streamId is, MUST a {@link ClientDataMissingException} be thrown.
	 * 
	 * @return A String with the streamId for this link.
	 */
	String getStreamId();

	/**
	 * setFileName sets the fileName for this link.
	 * <p>
	 * If there is a fileName since before must it be replaced by this method so that only one
	 * exist.
	 */
	void setFileName(String fileName);

	/**
	 * getFileName returns the fileName for this link.
	 * <p>
	 * This information is expected to be present, if this link does not have information about what
	 * the fileName is, MUST a {@link ClientDataMissingException} be thrown.
	 * 
	 * @return A String with the fileName for this link.
	 */
	String getFileName();

	/**
	 * setFileSize sets the fileSize for this link.
	 * <p>
	 * If there is a fileSize since before must it be replaced by this method so that only one
	 * exist.
	 */
	void setFileSize(String fileSize);

	/**
	 * getFileSize returns the fileSize for this link.
	 * <p>
	 * This information is expected to be present, if this link does not have information about what
	 * the filesize is, MUST a {@link ClientDataMissingException} be thrown.
	 * 
	 * @return A String with the fileSize for this link.
	 */
	String getFileSize();

	/**
	 * setMimeType sets the mimeType for this link.
	 * <p>
	 * If there is a mimeType since before must it be replaced by this method so that only one
	 * exist.
	 */
	void setMimeType(String mimeType);

	/**
	 * getMimeType returns the mimeType for this link.
	 * <p>
	 * This information is expected to be present, if this link does not have information about what
	 * the mimetype is, MUST a {@link ClientDataMissingException} be thrown.
	 * 
	 * @return A String with the mimetype for this link.
	 */
	String getMimeType();
}
