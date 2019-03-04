package com.glic.hsm.payshield.enums;

public enum MaskGenFunctionHashEnum
{
    SHA1("05"),
    SHA224("06"),
    SHA256("07"),
    SHA384("08"),
    SHA512("09");

    private String commandValue;

    private MaskGenFunctionHashEnum(String commandValue)
    {
        this.commandValue = commandValue;
    }

    /**
     * Get the thales command value for the enum
     * 
     * @return thales command string
     */
    public String getCommandValue()
    {
        return commandValue;
    }
}