package com.enercon.model.comparator;

import java.util.Comparator;

import com.enercon.model.dgr.StateCustomerDGR;

public class StateCustomerDGRComparator {

	public final static Comparator<StateCustomerDGR> BY_STATE_NAME = new Comparator<StateCustomerDGR>() {
		public int compare(StateCustomerDGR o1, StateCustomerDGR o2) {
			return sortByStateName(o1, o2);
		}
	};

	public static int sortByStateName(StateCustomerDGR o1, StateCustomerDGR o2) {
		return StateMasterVoComparator.sortByName(o1.getState(), o2.getState());
	}
}
