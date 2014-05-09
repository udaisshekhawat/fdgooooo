package com.foodango.testing;

public enum BackEndServer
{
    PRODUCTION("https://www.britishgas.co.uk"),
    INACTIVE1("https://10.224.19.44"),
    INACTIVE2("https://10.224.19.46"),
    INACTIVE2_Exter("https://193.132.48.46"),
    INACTIVE1_Exter("https://193.132.48.44"),
    DIGITAL02("https://10.224.70.17"),
    DIGITAL03("https://10.224.70.18"),
    DIGITAL04("https://10.224.70.32"),
    DIGITAL05("https://10.224.70.36"),
    DIGITAL06("https://10.224.70.37"),
    DIGITAL07("https://10.224.70.44"),
    DIGITAL08("https://10.224.70.51"),
    DIGITAL09("https://10.224.70.55"),
    DIGITAL10("https://10.224.70.59"),
    DIGITAL11("https://10.224.70.76"),
    DIGITAL13("https://10.224.70.83"),
    DIGITAL16("https://10.224.70.95"),
    DIGITAL17("https://10.224.70.100"),
    UAT("https://10.224.70.74"),
    EXTERNAL_UAT74("https://193.67.162.139"),
    EXTERNAL_DIG_01("https://193.67.161.46"),
    EXTERNAL_DIG_02("https://193.67.163.21"),
    EXTERNAL_DIG_03("https://193.67.163.41"),
    EXTERNAL_DIG_04("https://193.67.163.63"),
    EXTERNAL_DIG_05("https://193.67.163.50"),
    EXTERNAL_DIG_06("https://193.67.164.24"),
    EXTERNAL_DIG_10("https://193.67.163.23"),
    EXTERNAL_DIG_11("https://193.67.164.39"),
    EXTERNAL_DIG_UAT_111("https://193.67.163.141"),
    EXTERNAL_DR_CLUSTER_2("https://193.67.162.253"),
    TEST_HARNESS("http://192.168.1.101:8080"),
    GLASSFISH_TEST_HARNESS("http://192.168.1.158:8080");

    
    // TEST_HARNESS("http://213.225.134.145:8080");//hosted on www.britishgasmetering.co.uk: don't know the
    // password!

    private String url;

    private BackEndServer(String url)
    {
        this.url = url;
    }

    public String getUrl()
    {
        return url;
    }

    public static int indexOf(BackEndServer server)
    {
        BackEndServer[] values = values();
        for (int i = 0; i < values.length; i++)
        {
            if (values[i] == server)
            {
                return i;
            }
        }

        return 0;
    }
}
