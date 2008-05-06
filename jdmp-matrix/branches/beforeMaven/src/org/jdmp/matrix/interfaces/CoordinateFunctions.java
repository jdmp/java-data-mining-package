package org.jdmp.matrix.interfaces;

import org.jdmp.matrix.MatrixException;

/**
 * This interface defines functions on a Matrix that have to do with the
 * coordinates of the entries.
 * 
 * @author Holger Arndt
 * 
 */
public interface CoordinateFunctions {

	/**
	 * Returns an Iterator that goes over all coordinates in the Matrix. It goes
	 * from 0,0 to the size of the Matrix.
	 * 
	 * @return Iterable over all coordinates within a Matrix.
	 */
	public Iterable<long[]> allCoordinates();

	/**
	 * Returns an Iterator that only goes over the coordinates in the Matrix
	 * that are stored. For most Matrices, this is the same as allCoordinates().
	 * For sparse Matrices, it iterates only over the entries in it.
	 * 
	 * @return Iterable over the saved entries in a Matrix.
	 */
	public Iterable<long[]> availableCoordinates();

	/**
	 * Returns an Iterator that goes only over the Coordinates defined by the
	 * selection. The selection is a Matlab/Octave style String, to define what
	 * rows or columns should be considered. E.g. "(2:5,[1,3,5,7:9])" to select
	 * rows 2 to 5 and the columns 1, 3, 5, 7, 8 and 9. Note that, in JDMP
	 * numbering starts at 0 unlike in Matlab and Octave
	 * 
	 * @param selection
	 *            The String defining the selection of rows or columns
	 * @return Iterable over the desired Coordinates
	 */
	public Iterable<long[]> selectedCoordinates(String selection);

	/**
	 * Returns an Iterator that goes only over the Coordinates defined by the
	 * selection. The selections consists of a list of long arrays, one for each
	 * dimension. The first array contains the row numbers that should be
	 * selected, the second the column numbers, and so on.
	 * 
	 * @param selection
	 *            A list of long arrays defining the desired rows or columns
	 * @return Iterable over the desired Coordinates
	 * @throws MatrixException
	 */
	public Iterable<long[]> selectedCoordinates(long[]... selection) throws MatrixException;

	/**
	 * Returns the position of the maximum value in a Matrix. If there is more
	 * than one equal maximum values, the first that is found is returned (not
	 * necessarily with the lowest coordinates). If no maximum can be found
	 * (because there are no numbers in the matrix or all numbers are NaN), the
	 * coordinates -1,-1 are returned.
	 * 
	 * @return Coordinates of the maximum value
	 * @throws MatrixException
	 */
	public long[] getCoordinatesOfMaximum() throws MatrixException;

	/**
	 * Returns the position of the minimum value in a Matrix. If there is more
	 * than one equal minimum values, the first that is found is returned (not
	 * necessarily with the lowest coordinates). If no minimum can be found
	 * (because there are no numbers in the matrix or all numbers are NaN), the
	 * coordinates -1,-1 are returned.
	 * 
	 * @return Coordinates of the minimum value
	 * @throws MatrixException
	 */
	public long[] getCoordinatesOfMinimum() throws MatrixException;

	/**
	 * Determines if the given Coordinates are part of the Matrix. If the Matrix
	 * is dense, true is returned for all Coordinates smaller than the Matrix's
	 * size. For sparse Matrices, this function checks if the coordinates are
	 * actually stored in the matrix or not.
	 * 
	 * @param coordinates
	 *            The coordinates to check
	 * @return a boolean stating if the coordinates are part of the Matrix
	 */
	public boolean contains(long... coordinates);

}