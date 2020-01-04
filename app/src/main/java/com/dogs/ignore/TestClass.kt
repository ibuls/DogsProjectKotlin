package com.dogs.ignore

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import com.dogs.utils.log
import com.dogs.utils.toast
import java.io.Serializable


public class TestClass() : Parcelable {
    constructor(parcel: Parcel) : this() {
    }

    fun testMethod(context: Context?){
        context?.toast("Test Class Called")
        context?.log("Test Class Called")
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TestClass> {
        override fun createFromParcel(parcel: Parcel): TestClass {
            return TestClass(parcel)
        }

        override fun newArray(size: Int): Array<TestClass?> {
            return arrayOfNulls(size)
        }
    }
}