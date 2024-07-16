package com.advent2023.days;

import com.advent2023.days.utils.Utils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Day15 {

    private static final int EXPECTED_RESULT_DEMO_PART_ONE = 1320;
    private static final int EXPECTED_RESULT_DEMO_PART_TWO = 145;
    private static final int EXPECTED_RESULT_PART_ONE = 498538;
    private static final int EXPECTED_RESULT_PART_TWO = 286278;

    private static final String DEMO_DATA = "rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7";
    private static final String DATA;
    static {
        DATA = "tsqxjb=8,hr=6,dtv=1,chhq=8,lcrbv=6,szm-,mx-,xj=4,dmm-,prdgn-,cnk-,tl=5,zhc-,xvcp=8,mpvh=5,xff=4,tx=6," +
                "vgr-,hkh=4,zjm-,kfj=5,gg=4,nltf=1,hc=1,rsn=7,hqs-,zsrs-,qjpjv=4,zxf=6,jrt-,sc-,gtnx-,jcl-,dm-,nch-," +
                "ct-,lh-,qzd-,rvv=9,vkqzk-,sq-,ptp=4,hng-,mmf-,vj-,nfdkq=6,hv=9,nsp-,fh-,fbn=5,bnmft=8,xtnl=8,xsb-," +
                "st=9,bjgs=7,pk-,slt-,jlxf-,zxf=7,qx-,scd=2,clh=3,xjdg-,flf=5,rrh=8,mg=7,bjvn-,cqcn-,pvr=7,rlsj=4," +
                "hxn=9,cds-,mhr-,lt=3,qcm=8,rfh-,gpknq=9,cp=2,msc=1,bg-,vmt-,jr=4,pxdlpv-,jkmvr-,rqxk-,hjx-,jr-," +
                "szm=1,sh=8,hrn-,lpsk-,ts=7,jdkkq-,czv=9,fddj-,gqgbf-,kkk-,bds=4,fldg-,dhb-,gx=4,dlf=4,lq=2,kx=1," +
                "bzhl=8,nqfn=6,vfpx-,bt=2,gnx=4,hrn=3,pxdlpv-,qjf-,rqxk-,pf-,nlcv-,srfl=7,zg=2,rjm=2,kgx=7,psc-," +
                "php=3,bj-,bds-,st=4,dfj=4,qrfx-,sgqlsb-,frp-,pf=9,lm-,jn=6,srfl-,njnpq=8,mpvh-,ghcd=7,dm-,kzbc=6," +
                "lh=8,jtc=6,qcqk=2,jm=3,vlcvb=7,tnz-,zfrs-,nqfn=5,cv=6,prdgn-,cqcn-,njnpq=6,bvt=3,hhp=6,hrn=5,bj-," +
                "jflv=9,hbh=2,ggd=9,qp-,cx-,pmhz-,ptp=3,jvnjh-,zz=5,bjbrrv-,gmvc-,qr-,fp=2,tqd-,bx-,qcqk=5,nm=5," +
                "jrc=1,ht-,ddz-,bn=6,dlnc=5,zvzc-,vf-,zxnq=4,ltxz=6,qrd=6,jtc=6,fgr=2,vcmrn-,tch-,fp=3,zrt-,slt=5," +
                "cn=1,sb=7,zd=2,cxptjr-,hc-,vfpx-,fx=3,gkj-,jjpk-,nh-,fh-,mpvh-,cn-,gdtqh=7,bctx=3,qsv-,gbv=1,gmvc-," +
                "gcf=7,rrh=4,cpds-,shxm-,hr=6,gz=5,rlsj-,pj=5,cqrh-,ggd-,sc-,nb-,fn-,pzv=1,cs-,ttj=4,tr=2,cdq=9," +
                "sntf=9,brx-,ht=2,jkjrx-,zhc-,vjtk-,tzdz-,qkmnn=8,vbd-,jlxf-,fc=6,xsl-,sp=4,ltxz=3,pmgjkd-,kl-,ggdb-," +
                "gl=5,hf=7,nqfn=1,bbjb-,sgnz-,px=3,dj=2,fq-,tcs-,zxf=8,rlsj-,hc-,frp=9,cds-,cqp-,bffjx-,khnv-,nvrn-," +
                "sg=1,zsfls=3,gpknq=5,mx-,czzf=9,jd-,gmvc=4,qjpjv-,qfl-,fh-,zvzc-,zsm=4,nj-,cqnpv-,fmm-,dqrx=8," +
                "fpmhbp=1,nfdkq-,rd=3,thq=6,mb-,cs-,lrlj=4,ht=3,slt-,hz=6,ggd-,ljr-,gtnx-,bctx-,bq-,stf-,shdd-,dpg=8," +
                "cpq=2,zdqxlx-,mzqmg-,nhq=2,qfv=4,qzft=8,gkxmct-,fcn-,xl-,gqgbf-,jxkh-,ctbxs=3,vkqzk-,bjf=9,njrg-," +
                "dsc=3,dsc-,gckl-,mdq=3,sgg=6,flf-,rfh=9,znzbpb-,gf-,fbn=6,xjb-,fnz=6,qkk=7,sxk-,lqh-,cqcn-,qzft-," +
                "hqs-,cs-,fq-,xhjtm=9,fcn=1,xrqhbm-,nrrr-,cq=2,ds-,fq-,tl=7,lk-,lcrbv-,bp=2,hqs=9,mmjb-,hm=8,dnx=8," +
                "sz=4,zgrn-,gp-,jn-,ccr=5,cnk-,hng-,sc=4,jmp-,zg=6,lqfr-,fz-,bz=4,jhd-,kskj=8,xvcp=8,fsgl-,ggt=4,pf-," +
                "ds=6,sc-,zhl-,vjtk-,gdg=7,fmb-,qr=9,tcs=3,lq=1,thq-,zhc-,ggdb-,bctx=6,zd-,fc=4,xr-,ddz=4,gfps-," +
                "lzs=9,qmj=9,qfv=5,hk-,hk-,vnh-,ggt-,dg=7,gx=4,zfr=4,jd-,njrg-,ttx=7,gtnx=4,nhq=2,mbdcz=1,pjc-," +
                "ngttt-,sq=3,zmk=1,hsq-,qrd-,ttjrn=3,dr=2,xtnl=6,nbg=1,rkx-,dcr-,qsv=6,vfpx=3,ld-,lzzb-,jdh-,tch=4," +
                "gdg-,kc-,rqgp=6,bt-,bj=7,stf-,zmd-,vfh-,hm=2,cv-,knmdd-,hz=7,pmgjkd=8,xsb-,jkjrx-,jdkkq-,qkmnn-,rr-," +
                "dsc-,mdq=3,pld=5,sq=9,mt-,sxk=8,xqqdv-,drv=1,qfv-,shdd-,xc=1,nbg=7,ptp-,dtv=8,ghcd=9,skrn-,qzd=7," +
                "gtnx=9,xrqhbm=1,nlcv-,bfvzh-,jflv-,hnf=2,dqrx-,gg-,tqd=4,dg-,lzs=3,ptmbcq-,gt-,hv=4,sg-,sgnz-,vh-," +
                "tnz=5,fk=5,bds-,dpj=5,xgq=2,xhjtm=6,dt-,nf-,jvzb=2,sn-,hv=3,lfbxds=5,qkmnn=7,qg=2,hxn-,pvr-,qg=4," +
                "zn=1,vv-,frp-,dt-,rrh=4,vtm=7,dcr-,jkjrx-,mbdcz=9,td-,rq=2,stg-,mdps=3,ggd-,dhb=7,mkm=9,mdps=2,xr=7," +
                "txbr-,tzz-,tk-,dhb-,rtct-,zmd=4,jc-,ttjrn=2,pvr-,hhp=9,dcr-,rg-,mdq-,jr-,khnv=7,rk-,jz-,bqq-,cpds-," +
                "tncjgs=5,ml=5,vx-,bjgs=9,mmf=9,dfj=8,rqgp-,pjtzxc-,tx=4,shdd-,jp-,qg=7,pvr=2,nnm=4,bzhl-,pzv=8," +
                "ttjrn-,qkk-,pc-,fsgl=7,hz=9,ttjrn=8,bj=5,xhb-,sg=4,tt=3,rkr=5,tqd=9,vtm=6,rvv-,vfh=4,sdkkg-,rt-," +
                "zxf=3,gpknq=2,php=4,cxptjr=1,gpknq-,psc=8,tcs-,khnv-,fk-,rq-,ngttt=9,gdtqh-,hk-,jz=3,xndq=5,ngttt=8," +
                "vh=4,mb=8,gdm=1,cbnfl=7,rbhl-,vhvpk-,lcrbv=2,knb=7,pmhz=9,tk=4,cf-,tl-,xn-,bvt=1,dlf=1,fhx=4,xsb=4," +
                "sntf=4,hf=1,sh-,vzscd-,nlcv-,gq-,bc-,qr=5,qr=2,nl-,mmjb=2,gqgbf-,gdg=7,vfh=8,pmgjkd=7,vg=2,sq=5," +
                "gg=3,zvb=5,gg=9,zrt=7,qmj=1,xn-,rlsj=1,clh-,dhrgg=4,czzf-,ds-,zhl-,fh-,dhq=3,lh-,hkh-,kkk-,hkh=2," +
                "dlnc-,thq=8,ttjrn=1,kgf=5,hqs-,zsm=8,lt-,nltf-,stf=4,ld-,qp=8,bbjb-,fzfg=5,zd-,vmt=8,lpsk-,dv-,crc-," +
                "hc-,xj-,rd-,qsv-,zmk=6,vtm-,lc=3,th=4,lt-,bbb=3,xff-,znzbpb=7,gdtqh=3,mzqmg-,kfj-,xndq=8,czv-,jjpk-," +
                "msc=5,fh-,jjpk=7,zmk=1,cf-,zfrs=6,rt-,zvzc=5,jvzb-,hr-,lt-,hcnchc-,pld=7,svz-,gf-,cn=1,gkj=3,rz-," +
                "fc-,rg-,zmd=5,pmhz=5,szj=4,ltxz=1,lm=5,qcm-,jxlg-,gckl-,znx-,xtr-,mpvh-,lq-,zdjkk=9,vl=9,zkdjf-," +
                "cvpp-,xl=9,fcn-,thq=4,zdpcrj-,mb=7,tt=7,dph=7,zfrs=7,fzfg=3,jp-,rd-,pmgjkd-,pmhz-,qpnf-,qfmjv-,ljr-," +
                "ds-,bjvn-,rd=4,cdq-,pmhz-,sb-,ngttt-,qfv-,cpnx-,hqs=5,qcqk=8,vhvpk-,jhd-,jtc-,bjbrrv=9,hjx=5,gx-," +
                "nh=9,xnj=5,fpmhbp-,jnzl=2,jpmnk=9,fhx-,qjf=3,mc=6,qp=3,nz-,fx=8,bjvn=8,fgr=4,fhgz-,jvzb-,scd=3," +
                "rvv=6,sg=3,tsqxjb=1,sgg-,zhc=8,dqk-,jhd=8,qjf-,nr=9,dph-,fp=4,nb=3,vnf=5,kx=9,slt=2,sg-,nvld-," +
                "knmdd=3,msc=9,mbdcz-,zsfls-,fnz-,hf-,svhqlj-,nf-,xj-,rt=1,gx=8,szm=2,bjgs-,dhz-,pk-,kk-,knb=7,tr=3," +
                "frvmt-,zhl-,tj=7,dg=9,ml=9,xhjtm-,frvmt=9,snhdx-,pmgjkd=9,dfj=9,sx=1,bg-,qzft-,jrt-,nhq=4,tk=6,bd=5," +
                "fbn-,lh=1,qcqk=2,jmp-,nh-,zmk-,hnf-,shb-,stg=6,tsqxjb=1,dznp-,pjc-,lpk-,gbv=5,rg=8,ggt=3,xtnl-,mp=3," +
                "xjdg=6,vf-,zsrs=2,ht=9,ttj-,dg=4,tx-,rrh=2,qfl-,msc=6,bbjb=3,ct=1,fhgz-,bjf-,dpg=8,xn-,sgnz-,xc=4," +
                "qh-,bnmft=4,zgx-,gzbg-,qzft-,hkh=8,stg-,czv-,rs=1,dlf=6,brx=9,xqqdv-,hsq-,hjx=4,hm=3,lx-,kzbc-," +
                "czzf=3,dznp=9,gfq=8,mkm=4,bjbrrv-,bzhl=1,lfbxds=9,tsz-,rvv=7,ldvl-,xl-,sc-,pmhz-,lzzb-,cp=6,nfdkq-," +
                "kzbc-,rm-,hscr-,rd=6,nqbl-,rz-,gg=3,gdg=6,vhvpk=5,cbnfl=8,kxsj=1,fvd=4,bjbrrv=8,td=1,rvv=8,bfvzh-," +
                "mb=2,px-,vx-,xc-,bjst=1,dzf=3,pjtzxc-,stf=7,gckl-,stg-,kkk=9,vgk=8,brx-,cf=1,lfbxds=5,bg=1,bcdqgj=5," +
                "scd-,jlxf=7,sz-,bjq=2,qkk-,ccr=3,cdq=8,ltxz=4,nltf-,qghqc-,dhq-,kgx=2,dj-,cxptjr-,qr=4,lt=6,rz=3," +
                "dlnc-,bzhl-,jvtl=3,skrn=3,rvv=9,rbhl-,qjpjv=6,stg=1,gtnx-,bqhxd-,slt-,dj-,xhjtm=4,vl-,mmjb-,cpq=8," +
                "xqqdv=2,qfl-,vj=6,kk-,zgx=9,qjpjv=9,tt=5,lpsk=7,vnf=8,dr=3,fnz=9,jkmvr=1,xsl-,bq-,bfvzh-,rvv=6," +
                "sgqlsb=4,sc=6,qfl-,zxf-,frp=3,clh=8,rt-,zxf-,xjb-,tsqxjb-,hhp=6,mnm-,gz-,nrrr=8,mbdcz-,fvd-,dznp=5," +
                "nbg-,vlcvb-,xsb=9,qjpjv-,xtnl-,gbv=2,cnk=6,jflv-,vgk-,lrlj=3,hf=9,dlf=7,bbjb-,lfbxds-,bcdqgj-," +
                "ctbxs-,fdc=2,mzqmg=5,kxqg-,qh=9,hnf-,xhb-,pc=3,ff=3,jhd-,dlnc-,bqq-,svz-,kskj=7,nnm=6,xtr=6,sntf=3," +
                "jpmnk=9,tms-,tsz-,dhz=1,nltf=2,phljs=2,ht-,szj=7,mpvh=2,tnngr=4,zkdjf=1,zvgx-,txbr-,bn-,rlsj-,sb=2," +
                "rkr-,hjx=8,ggt-,zjm=4,dfj=6,lpk=3,flf=4,vtm=6,qzd=3,dph-,bx=8,bctx-,ngttt-,skrn=6,dzf-,zdjkk=9,zs-," +
                "ljv-,jnzl-,bsxfk=3,xxz=5,hfcjfx=2,bctx=4,bzhl-,tch-,dj=7,vfpx=1,dlnc=3,hs=5,bffjx-,flf-,pxdlpv-," +
                "kvzd=2,pjc-,dpg=9,drv=1,rjm-,mc=8,qsv-,scd-,ds-,ldvl=6,bj=6,bds=4,qjf-,lzzb=3,lrk-,lqh-,shxm-,ds-," +
                "zn=5,cqp=5,ddz-,pnblf-,lfbxds=2,nch-,zjm-,spmz-,zfrs-,fzfg-,pxdlpv-,bffjx=7,td=4,dbs-,dsq=4,kcgr-," +
                "xgq-,qkk=4,xn-,rbhl-,kcgr-,qfv-,xrqhbm-,kl-,vtm=9,znzbpb=6,lm-,mm=3,bfvzh-,frp=9,jr-,slt=3,xndq-," +
                "bjvn-,qx-,sgnz-,dr-,mqlfsf=6,skrn=6,qdqp-,ctbxs=2,nltf-,mp=2,rm-,vtm=5,nch=2,shdd=8,jqfs=6,lrk=5," +
                "pj-,hqs=3,vvf=2,qrd=2,pvr-,zdjkk-,hc-,dg-,cpnx=2,rbhl=9,bn-,cqrh-,kvzd-,vrq=8,kcgr=7,lt-,hk=5," +
                "nbdsxb-,bp-,zxnq=8,tdg-,vcmrn=5,cpq=3,qp-,fc-,rz=9,dlf-,gf-,ksg=1,zn=6,ftd-,spmz=2,sxs=6,nhq=2," +
                "lfbxds=1,rfh-,sgg=1,nch=6,gqgbf=7,rt=6,dm=5,fbn-,nrrr=5,vhvpk=1,ljr-,bbjb=2,kfj=4,fzfg=7,qpnf=3," +
                "ttjrn-,qm-,rbhl=1,khnv=4,rx-,cbnfl-,dg=1,gfps=2,hrn-,tncjgs=6,tqd-,fc-,tj=8,mp-,tms=5,vgr=5,jmp-," +
                "fz-,zkdjf-,shb=2,nj-,dbs=5,qzft-,mv=9,ptmbcq-,fp=6,frvmt-,qjf=6,hf-,ld-,zhc-,qzd=5,zrt-,rj-,vsck=6," +
                "fvd-,czzf-,qfmjv=8,knv-,hm=2,cq-,cf-,mm-,dt-,cpnx-,rsn=1,dm=9,bslt=7,hm=8,jkmvr-,zkdjf-,qfl-,nrrr-," +
                "ts-,nj-,jp=3,qpnf-,lcrbv=3,fn-,fc=9,bn=4,xvcp=2,gcz=2,psc=5,bqq-,bd=7,rd-,ftd=7,nfdkq=6,srfl=8," +
                "vhvpk-,lh=6,khnv=5,dfj-,bp-,xqqdv-,cq=6,vsck-,cx-,tj-,szj-,mdps-,gq=2,fc-,lzzb=2,lt=5,zxf=5,crc=7," +
                "jvnjh-,cxptjr=4,vv-,zdpcrj-,jlxf-,cq-,gx-,xsb=9,bt-,jmp=4,cpq-,zsfls=5,ftd-,lpsk-,jkjrx=6,fn=8,ml=1," +
                "znzbpb=4,krhb=1,jc-,hr-,fzfg-,cqnpv-,lt-,nb=9,hv=3,zn-,xhjtm=1,vfpx=4,mmf-,fz=1,stg=3,zvb-,gmvc=2," +
                "vg=4,zfr=9,fvd-,gl=4,gqgbf-,bjq-,vf-,zd=7,bds-,gg-,ngttt-,dpg-,cz-,fcn-,rzd-,pkp=6,nvrn-,xndq-,nbg-," +
                "bzhl=8,bcdqgj-,nz-,fjv-,nbg-,thq=6,rd-,bvt-,cds-,qfl=7,gt-,dhq=9,lh=6,sb-,gdg-,fldg-,ds=9,vzscd=2," +
                "cqnpv-,xjb=9,nnm=8,fsgl=5,bds=2,hqs-,nh=1,tj=3,lqh-,njrg=4,hlx-,kxsj-,dhb=2,qmfxv=8,fz-,mt-,vgk-," +
                "gq-,lqfr=4,pxdlpv=7,xn-,frvmt=9,lpt-,dr-,fvd=9,bjvn-,gt=7,hjx=9,gnx=6,jr=6,gcf-,tdg-,mg=8,kgf=8," +
                "hrn-,td=6,sxs-,pk=9,xl=1,pnblf=3,ml=1,nz-,tx-,xrh-,dhb-,ddz-,fx=7,hbh-,vnh=8,dhz=7,knv-,ccr-,xxz-," +
                "nqbl=6,bjst-,rsn-,sxs-,qjpjv-,tzz-,hz=2,vbd=7,psc=5,bffjx-,dmm-,hbh=7,frvmt-,tnngr-,nvrn-,dpj=6," +
                "lzs=6,mv=3,qmfxv=6,hbh-,xtnl=1,dfj=1,zdjkk=3,qt-,sxk=4,cds-,jdkkq-,hnf-,mx=3,vzscd-,cpq-,vdfvp=9," +
                "hqs=2,rqgp-,xxz=5,hcnmh=9,hcnmh-,hfcjfx-,gkxmct-,bzhl=4,hng-,nr=3,xxz-,fq=7,dhb-,rbhl-,svz=1," +
                "gqgbf=5,frvmt-,vjtk=1,kl=9,hs-,jfp-,pkp-,dt=5,fdc=9,qx-,bg-,rq=8,vgr-,jxlg=7,tdg-,jtc-,zfrs=3,dhz-," +
                "rjt=5,pnblf=6,st=8,cdq=1,bsxz-,xjdg=2,zgrn=6,qx-,dqk=1,kzbc-,vn-,dpg-,pf-,dfj=9,jlxf-,znx-,hfcjfx-," +
                "qfv=5,zkdjf-,dhrgg=4,pmhz-,vcmrn=4,kkk=6,drv-,gmvc-,hsq-,lzzb=2,ggt=2,ddz-,bctx-,hlx=8,jrt=4,zs=9," +
                "bjt=9,bbb=3,fldg=8,cnk=3,vgk=4,stg=9,tsz=9,qfv-,lfbxds=8,sntf-,vn=2,vfpx=9,zkdjf=9,lh=8,qfl-,fvd=5," +
                "dv-,mt-,zsm-,nb=2,kvzd-,hr=4,njnpq=7,dsc=9,nfdkq-,dqk=8,vcmrn=9,dsc-,dmm-,hfcjfx-,dqrx=4,qzft-,hlx-," +
                "ljv-,qkk=3,msc-,cqrh=9,xxz=2,kkk=9,lfp=3,qx=8,vgr=5,rkx=7,jrt=9,cn-,gd-,fcn=8,mhr=2,tr-,dtv-,cqnpv-," +
                "cpnx=3,cdq=6,vf=4,zvb=2,jdkkq-,gq=8,zdqxlx=4,chhq-,dsq=6,xgq=1,nr=5,bfvzh-,hnf=8,hcnmh=4,gmvc=2," +
                "nvrn-,hnf=7,vv-,zxf=1,dqrx-,gkj-,fq-,xl=8,kfj-,pjtzxc=8,stg=1,vx=5,ptmbcq-,zvgx=5,lq=4,mzg-,hcnchc-," +
                "mb-,ggd=7,nltf=7,zgrn-,xtr-,szm-,ggd=7,bffjx-,cvpp-,gckl-,cn=9,pfbkbm-,knv=7,kxsj=7,nrrr=9,ggt-," +
                "flf-,qrd=7,rd-,xc=5,gnx-,dhb-,qrd-,dmm=1,st=4,hhp=1,zmk-,bbjb=1,lc-,cnk-,shxm-,bt-,gqgbf-,vj-,bj-," +
                "ts=2,bq=1,qdqp-,gg=6,bjq=3,gfq-,bjbrrv-,cl=1,vfh=9,bjq=9,qn=7,flf=9,rkx-,cnk=3,fbn=6,gkj=6,bp-,jd-," +
                "mhr=1,scd=4,dnx=5,cnk-,vtm-,bjv-,shb=4,vhvpk-,cqnpv-,kxqg=5,ttjrn-,tt-,cqcn-,lpk-,bvt-,qn-,tzdz=3," +
                "bvr=5,pvr-,gf-,tvc=3,sxk=1,dlf-,vh=3,pld=4,kgf-,rqgp-,qsv=7,bbb-,bjv=5,lcrbv-,bqhxd-,zfr-,lqfr=9," +
                "zsfls-,zkdjf-,ttj=4,szj-,ghcd=9,pfbkbm=3,jvzb=6,ts=1,vzscd-,kgx=1,tzdz-,sq-,qn-,rbhl-,lrk=1,nhq=7," +
                "sgqlsb=6,xj-,bjst=5,cx-,xl=6,sh-,qdqp=5,jkr-,pjtzxc=5,vlcvb-,vkqzk=9,gz-,jqfs=3,thq=9,srfl-,tsz=3," +
                "hrn=3,ng=6,qghqc-,ljv-,vgk-,mc=1,slt-,lrk-,sq-,fbn-,jqfs=7,hk=1,jflv=1,zgx=2,hng=1,gpknq-,qpnf-,dm-," +
                "ghcd=7,lrlj-,bp=7,bcdqgj-,hz-,cqnpv=1,gfps-,qrd=8,sgnz=8,vvf=7,nsp-,hscr-,xjb-,zdjkk=2,vj=2,tms-," +
                "zmk=6,tqd=5,bd-,cpds-,cds-,qjf=6,gd-,tdg=4,kk-,sx-,bcdqgj=6,mss-,ctbxs=2,rrh-,tt=5,tk-,jfp=2,szj=6," +
                "qghqc=7,gp=3,njnpq-,nltf-,fjv=3,zz=6,kdmd-,cz-,sntf-,knv=8,sxs=4,hm=8,zdjkk=8,qm=5,xn-,vvf-,snhdx=7," +
                "shb=4,vvf-,sn=8,pmhz=4,sgg=2,jflv-,mv=3,ccr-,scd-,ltxz-,snhdx-,rk=2,pmgjkd=7,shb=7,xxz-,jvtl-,ds=3," +
                "kxqg-,zvb-,cqcn-,ts-,czzf-,dhrgg-,mmf=1,qp=1,mv=2,scd-,bt=9,gfq=5,rrh=8,gkj-,gcz=8,ng-,rkr-,cz-," +
                "fcn=5,pfbkbm=2,nqfn=3,cbnfl=9,ksg=1,ksg=9,sxs=4,lh-,mnm-,gcz-,nvld-,tqd=7,fmm-,vfpx=5,jlxf=5,tx-," +
                "gt-,vvf=1,dsq=8,thq-,fddj-,mhr=2,tt=6,nb=7,shb=3,zvb-,szm=4,cpq-,zmk-,hng-,sb=4,rbhl-,bctx=7,mp=1," +
                "gzbg=9,tnz=2,sx-,sgqlsb=5,sgqlsb-,vnf-,fddj-,mk-,bvr-,rvv-,kfj-,pzv=5,rz-,rqgp=6,bbjb-,hk-,sgg-," +
                "ngkmdh=5,ggdb=2,fk=9,hc=1,mv=5,vrq-,rlsj-,mbdcz-,sdkkg=4,nlcv-,pj=7,bjvn-,cqnpv=1,sp=6,kfj-,xr=1," +
                "pc-,jkmvr-,cds=2,ggt=6,brx-,xxz=9,bcdqgj-,zg-,fcn=4,vdfvp=7,ggt-,nsp-,hcnchc-,cf=6,jvnjh=6,sgqlsb=5," +
                "rg=8,kxsj=2,sc-,xff-,gdtqh-,ds=2,zdqxlx=6,ds-,lx=7,pkp-,mss-,dmm-,tcs=1,gp=4,gckl-,pjtzxc-,hm=5," +
                "lzzb=8,hsq=4,bqhxd=4,qfl-,gmvc-,ldvl=7,mmf-,vtm-,zdpcrj-,nnm-,bc=2,lcrbv-,kkk=4,php-,gkj-,bsxz=6," +
                "mm-,zdqxlx=6,sz=2,tnz-,nbdsxb-,ggd=6,zvzc=3,gbv-,gg=9,qx=8,gmvc=8,jkmvr-,slt=7,td=4,bc=1,drv=5," +
                "dznp=1,nq=9,ml=7,cv-,cnk-,xtnl=6,bzhl=3,nfdkq=3,dfj-,qg-,drv-,skrn-,tzdz=4,fn-,rr-,kl-,nfdkq-," +
                "kvzd=9,nvld=9,dtv-,lcrbv-,xr-,vh-,ljr-,kzbc-,gqgbf=8,frvmt=2,dbs=6,qx=1,gmvc=8,fn-,lx-,fn-,nbp-," +
                "dznp-,bfvzh=7,cq-,vzscd-,jnzl=3,ftd=2,vcmrn=2,vj=5,tnngr-,ldvl=1,qcm-,vf=9,nsp=2,jm=5,jvnjh=2,lq-," +
                "rg-,ml=6,rjm-,bjt-,szm=9,tsz=7,jflv=3,ltxz-,mkm=1,shdd-,mk=7,cqnpv=2,hrn=8,khnv-,qr-,vf-,xhb=9," +
                "jdh=3,jcl-,svz=5,hm=8,qfv-,fp=9,vzscd=2,zvb-,ddz=3,dvn=5,rbhl-,vhvpk-,rkx-,gd=3,rs-,hsq-,ksg=8," +
                "sntf=3,gg-,hm=9,jd=7,xn=5,njrg=3,jqfs-,prdgn=6,clh-,tqd-,psc-,drv-,gkxmct=6,jkjrx-,sx-,zmd-,vsck=5," +
                "sgnz=5,vgk-,fpmhbp=8,hcnchc=9,zdqxlx-,lx=2,gkxmct-,hjx=3,hng-,bjgs-,qxm-,bg=1,gckl=6,czv=2,gkj-,px-," +
                "dg=2,xrqhbm-,fn=3,nq=8,xsb=8,qmj=1,nch-,tdg-,gfps=2,hbh-,dbs-,bc-,dhq=5,gckl-,gl-,dt-,lpk=6,gqgbf-," +
                "fgr-,bx-,slt-,fgr=5,gkxmct-,lq-,bzhl-,tdg-,zfrs=6,mqlfsf-,fzfg=8,cqp=5,dznp=9,sntf-,xzbll=7,vl-," +
                "vkqzk=9,lrk=2,tnngr-,jz-,nh=7,znx=8,ldvl=4,stg=9,sntf-,dt=6,qrd=3,cbnfl=6,bqhxd=1,mnm-,fhgz=7,kskj-," +
                "vgr=4,jxkh-,lrk-,bffjx=7,dt-,xhb-,pfbkbm-,xrh-,xzbll-,gpknq=2,kxqg-,ggd-,qg=6,tsqxjb-,dph-,nz=9," +
                "crc=3,sc=3,nl-,qpnf=8,jkr=1,xhb=9,vnf-,qxm-,rjt-,czzf=4,rd-,nh=5,qxm-,rm=4,zxf=9,lfbxds-,km-,rs=4," +
                "gp=9,vrq=6,sgqlsb=1,gbv-,mv=6,bjf=5,ttjrn-,mk=1,xzbll-,gqgbf=9,jdh-,nj=5,xxz-,kcgr=2,frvmt=3,jm=1," +
                "kxsj-,jhj=6,tzdz=7,rkx=4,lt-,fgr-,rk=5,dmm=7,nlcv-,ff=8,xrh=4,ltxz-,cds-,bnmft-,gqgbf-,nz=6,ccr=7," +
                "mdps-,td-,tzdz=4,tqd=6,nnm=9,vnf=9,kfj=3,sg=8,qpnf=8,zdpcrj-,gtnx=1,mx=2,vdfvp-,rx=7,qcm-,rrh=9," +
                "rjm-,zz-,gd=4,xc=1,zn=3,vg=7,rz-,bjt=2,ptp=1,vkqzk=8,psc=1,fjv-,zg=7,rg=8,kdmd=5,fhgz-,tk=4,tx-," +
                "vcmrn-,gnx=3,hsq=3,kskj=6,xj-,zg-,zg-,bsxfk-,bsxz=1,hrn=9,qr-,czv-,pf=5,shdd-,kfj-,brx=5,dr-,mdps-," +
                "kc-,rg-,lrk-,jn=4,xr-,gqgbf-,lpk=5,tt=4,rt-,sp-,ttjrn=1,znx-,km=7,xzbll=5,mmjb-,kfj=4,kzbc=8,qsv-," +
                "px=2,gdtqh=2,lt=6,qpnf-,xl=3,kfj-,gf=5,rtct=5,khnv=6,tnz-,jfp=1,qp=1,fsgl=9,fvd-,nsp-,hcnmh=9,nvld-," +
                "prdgn-,hcnchc=9,hv=7,mzg-,nvrn=2,gq=4,frp=2,khnv=8,tvc=4,kxqg-,bfvzh-,fcn=1,kcgr-,dzf-,vvf-,zxnq-," +
                "cvpp-,bq-,fvd=7,shdd=2,pxdlpv-,pfbkbm=4,dhz-,czv=2,zd=2,qfmjv=3,qn-,tt-,lrlj-,hr=6,chhq-,vcmrn=7," +
                "vfpx-,mzg-,kc-,gkj-,dhz-,xtnl=8,qh-,lc=9,gp=6,gkxmct-,bp-,nlcv-,tr=6,jvnjh=5,gf-,vmt-,vrq-,qfmjv-," +
                "bjv=9,fz=1,cz-,qjpjv=7,ff=6,kvzd=9,mk-,zdjkk-,qn-,hsq=9,qdqp=2,zjm-,pf=1,ggt-,jjpk-,tk-,jdh=9,gq-," +
                "lk=1,dfj=9,brx-,bjf=3,szj=7,jtc-,cs-,hf-,px-,nsp=1,zmd-,gmvc=6,dj-,rlsj=5,nrrr=6,nz-,cs-,zfrs-,hc=5," +
                "qkk-,mg-,chhq-,sq-,nfv=3,gg-,vh-,kfj-,lt=5,vh-,jfp-,nfdkq-,jrt=7,zsfls=4,qrfx-,sz-,bvt-,zvzc-,ml-," +
                "fgr=1,szm=8,rtct=1,rkr=5,jhj=2,xsl-,zgx-,pxdlpv=1,jhj-,mt-,xsl-,mkm=8,kl=8,qrfx=1,dqrx=8,ggd-,zfrs-," +
                "mmjb-,bvt=9,cz=5,qm=7,ljr-,mdps-,dfj-,gnx-,xbcm=1,bx=6,zhc=5,qjpjv=7,ptp=5,ljv-,gdm=9,dv=1,tx=5," +
                "pnblf=4,mnm-,tl=9,hr=2,jmp-,ts-,dlf-,xsl=1,dfj=8,dsc-,gc-,tnngr=9,dph=7,bjt-,fsgl=4,rjt-,njnpq=5," +
                "gdg=8,njrg=5,jvtl-,bt-,kl-,svz=6,mkm-,jqfs=4,rr=5,rs-,cpds=9,xvcp=3,gdm=3,bjq=8,tnz-,fjv-,stg-,lk-," +
                "dcr-,nltf-,zdqxlx-,nqfn=3,hk=6,xl-,rk-,vg=4,hsq=7,cqrh=8,zgx-,khnv-,sgg-,php=3,lfbxds=8,dzf-,pkp=5," +
                "nb-,qpnf-,xqqdv=9,vcmrn=4,frvmt-,nbp-,xgq-,rzd-,zfr=5,xvcp=4,bffjx-,gz=9,xtnl-,fk=8,vfpx=9,sntf-," +
                "nrrr=9,bp-,gkxmct=4,qm-,prdgn=4,jxkh-,nb-,nfdkq-,psc=9,zsfls=6,psc=4,dmm=4,qkk-,qh-,bj=6,gdm=6,zz=4," +
                "sdkkg-,thq=8,hjx=7,snhdx-,bslt-,hf-,mhr=1,bjf=7,vlcvb=9,bd-,ttj-,qpv-,prdgn=3,gdm=8,mkm=7,hmj=4," +
                "kcgr-,fsgl-,qmj=1,bz=2,zmk=1,hlx-,cpds-,fhx-,nbdsxb-,rsn-,tnz-,nfv=9,sq-,bz=9,ts-,fjv-,vh-,tcs-," +
                "zn=1,ccr=3,zhl=5,qfv-,mpvh=5,shxm-,rvv=1,msc-,qjf-,gq=4,sdkkg=5,fk=4,pjc-,mzg=9,nrrr=2,dhrgg=2,zs=8," +
                "hv-,dph-,rr=3,rbhl-,bq-,xndq=4,qsv=5,dfj-,dlf=5,vh-,jtc-,qmfxv-,kzbc-,prdgn=9,mzg-,nbg-,jqfs=3,gd-," +
                "bvr=4,cl-,gcz-,jrt-,fdc=7,lpsk=1,hrn=8,dfj=3,cqrh-,rqxk-,bjbrrv=7,dhb=8,jrc=6,zd-,bvt=9,fx=8,ldvl-," +
                "zvgx-,ljr-,snhdx=5,bz-,ljr-,jrc=4,lh=2,jkmvr-,pc-,jn-,qg=1,hr=3,lrk-,ltxz-,lk=4,dm-,ftd-,sx=9," +
                "vhvpk-,qjpjv=9,qfmjv-,ttx-,sh-,qt-,kxsj=3,hv-,xrqhbm=7,rs=8,ksg=8,cn-,vdfvp=5,tncjgs=2,hcnchc-,cz-," +
                "tms-,lzzb=1,snhdx-,pmhz=6,kfj=6,sh-,gkj=9,zkdjf=4,rqgp-,rq=4,gdm-,tms-,zfr=7,lc=2,ct-,fjv=6,nbp-," +
                "jhd=6,cv=7,zd-,mss=4,vcmrn=9,bbjb=1,qzft-,slt-,vbd=3,vrq=6,nnm=5,nl-,dr=6,spmz-,nbp-,sxk=5,znzbpb-," +
                "vfpx-,hcnchc-,gcf-,jhj-,vlcvb=1,pk-,jcl=2,zz-,cs-,nvrn=2,zgx-,xjdg=4,bd=4,sgnz-,fjv=4,psc=9,gz=5," +
                "nq=9,kcgr-,xtr=8,mx-,nltf=5,tsqxjb=1,zgrn-,fhx=4,vhvpk=3,nm=2,tr=5,qkk-,pnblf-,kcgr-,pmhz=5,tnz-," +
                "dpg=8,msc-,fmb-,lpt=9,pxdlpv=5,tnz-,hnf=7,srfl-,hf=5,bqq=2,dr-,krhb=7,nfdkq-,mm=1,ml-,xhb-,qp=9," +
                "jfp=3,zdjkk-,xr-,rkr-,czzf=2,hcnmh=7,xrh=5,jkjrx=7,dfj=6,pj-,shb-,kgx=8,fmm=5,gg=5,jxlg=3,xjdg-," +
                "zfrs-,xr=3,qkmnn-,gcf=3,st=6,ngttt-,vnh-,crc-,xhb=9,bqhxd=8,lcrbv=2,dznp-,vzscd-,bn-,zrt=4,nj-," +
                "ltxz=8,rr-,qm-,tcs=5,bqq=2,qx-,sq=2,zz=3,drv-,rvv-,php-,nh=6,chhq-,nz-,lx=5,dm=4,vfh=6,cpnx-,kcgr=8," +
                "jz=8,qxm=8,xj=8,lqh=4,zkdjf-,hnf=4,ct-,bq-,hc=5,hc-,nqbl=9,lrlj-,jjpk-,sn-,drv-,gdg=5,pxdlpv-,rx=1," +
                "cp-,cdq=4,vgk=7,kl=9,rs-,xff-,bzhl=4,dqrx=6,mzg-,xbcm=4,bnmft=7,vl=1,nqfn-,hv-,dfj=5,zfr=7,sz=1,rg-," +
                "cqrh=3,bqq=3,st-,bctx=8,kc=7,hc=3,ljv=3,zg=7,vg=2,pjtzxc-,gbv=6,jrc=1,qcqk-,jmp=8,stg-,nfdkq-,kfj-," +
                "xl=3,xrqhbm=1,rrh-,stf-,vrq=9,gc-,qmj-,rk=9,fhgz-,vtm-,vx=5,drv-,cn-,sb=8,cx=2,bslt=1,qzd=2,hk=1," +
                "vf-,zn-,ld-,jrc=2,bt=1,zsfls=3,ttx=8,nlcv-,bjbrrv-,lfp=5,cf-,dj=1,vgk=4,jvtl-,khnv=3,cqrh=3,ts-," +
                "kxsj=1,pld-,hkh=2,hbh=3,shb-,tx-,bp-,dtv=4,bffjx=1,dm-,ljr-,ksg=4,vcmrn-,cvpp-,rjt=4,jr-,pf-," +
                "znzbpb-,cqnpv-,fc=3,dhb=8,rx-,fsgl=6,stf-,tch-,vv=6,cf=6,tzz-,qfmjv-,ngkmdh-,rjm=7,jz-,cqnpv-,cpq=1," +
                "ggdb=3,jxkh-,hmj=6,qp=7,vfpx=1,ggt-,qm-,vnh=9,cx=5,zfrs=1,fk=4,hqs=2,zdqxlx-,cpnx=7,hc-,qzd=5," +
                "dznp=6,dlnc-,fmm=2,shdd=2,gx=3,cnk=8,jdh=5,zdpcrj=9,gfq-,gnx-,qdqp=8,dlf=3,vfh-,kcgr-,xff-,xsl=9," +
                "njnpq=7,tzz=4,gbv-,qjf-,zsrs=4,tsqxjb=5,mk-,knv-,nvrn-,jxlg-,nrrr-,nf-,qdqp=5,cds-,gmvc-,qfmjv-," +
                "dv=2,hs=6,szj-,knv=4,vgk-,fbn-,hlx-,tcs-,ljv=9,qmj=9,vl-,skrn=6,vhvpk-,mzqmg=7,xc=8,czzf-,ftd-," +
                "jdh=4,nj-,mpvh-,ht-,cds=8,kcgr-,gtnx=9,xzbll=5,ftd-,sb-,pld-,vnf-,pnblf-,sq-,dlf=9,tcs=7,st-,vv-," +
                "ksg-,jm=7,cqcn-,kzbc-,hng=8,sc-,phljs=4,qrd=8,xgq=2,xxz-,shxm=3,mp=9,hnf-,nq-,mbdcz-,stf-,pvr=2," +
                "tsqxjb-,bctx=8,bj-,zsfls=8,psc-,kc=1,mhr-,hc=4,vg=3,bcdqgj=2,vjtk=3,hr=3,sxs=4,zxf=9,qmfxv=3,hqs-," +
                "bjq-,sz-,bqq=4,sp=8,lx=1,jvtl=5,zdqxlx=2,fc-,xnj=3,svhqlj-,bqhxd-,cf=8,lh=8,dvn=9,jdh=6,szm=8,gx=1," +
                "vnh=2,nb=6,vj=6,xhb=6,cv-,km-,dqrx-,rrh-,zgx-,knb=5,tsqxjb=2,rx-,vvf-,nbp-,hk-,ml=8,gg=6,txbr-,vtm-," +
                "rtct=8,dlnc-,dph-,jz-,rjt=5,nf-,pjc=5,kxqg-,fldg-,dsc-,vj-,cvpp=7,qjf=9,sp-,tch=4,tsz=3,zvgx=7," +
                "vdfvp-,zfrs=1,flf=1,jkmvr-,lzzb=4,mv-,gfps-,bz-,cds=5,nh=7,tcs-,msc-,pnblf-,ctbxs-,mt-,ggt-,mbdcz-," +
                "xsl=1,dsq=6,cn-,flf-,qm-,qdqp-,bctx-,vfpx-,jdh-,fk=9,gg-,ptmbcq=7,ngkmdh-,hfcjfx-,cdq=9,jhd-,kdmd=1," +
                "kxsj=5,snhdx-,jnzl=1,zz=3,zfr-,stf=5,vcmrn=8,xsb-,zgx-,bjbrrv-,xhjtm-,xj=4,xsb=4,tzz=9,qfv=8,ljv=4," +
                "nf-,hkh=7,srfl=7,dg=5,gx-,pnblf-,dqk=2,nj-,vg=1,nh=1,td-,dqk-,xvcp-,rs-,tzz=1,fq-,phljs-,lpk-,vf=3," +
                "hcnmh-,hr-,xl=8,fk-,ds-,zrt=1,lpt=5,xqqdv=2,vfh-,bsxz-,zxf=8,jp=8,phljs=3,tzz-,kskj=3,jxkh-,bsxfk-," +
                "gqgbf=3,mzg=4,kxqg-,lpsk=6,jn=1,qpv=4,nbdsxb=7,jxkh-,ts=4,ltxz=2,tj=9,xsb-,rqxk=6,cn-,jvzb=6," +
                "hcnchc-,gmvc-,mm-,lq=5,chhq-,dnx=6,qfmjv-,hcnmh-,vg=2,fvd-,bq=6,krhb=9,nvld=7,spmz=6,mdps-,cqp=6," +
                "bj-,scd-,snhdx-,rkr=2,vzscd-,rg=3,bjv-,fhgz-,dt=4,ttj=6,hmj=4,jpmnk-,jz=6,zkdjf-,phljs-,dzf=7," +
                "hfcjfx-,ld=9,dvn=9,scd=1,njnpq-,gdtqh-,php-,zxnq=4,thq-,rzd=3,ts-,nvld=1,jdh=1,sdkkg-,qkmnn-,pf-," +
                "mss=3,vl-,bjvn=2,jvnjh=4,fn=7,jdkkq=1,bt=4,cpnx-,qsv-,fzfg-,fjv-,dph=7,hz-,tdg=3,hxn-,jcl-,fp-,vn=4," +
                "kc=9,qxm-,shxm=1,qjf-,bg=9,ds-,mkm=4,bjbrrv-,qsv=9,czv=9,rfh-,ljv=2,ljv=1,jxlg=3,mb=6,dt-,tms=8," +
                "mpvh-,cv-,jvtl-,vv=9,ttx-,jkmvr-,dqrx-,gp=4,dhrgg=9,vhvpk-,kzbc-,vf-,cq-,gq-,hscr-,rj=8,ldvl-,nltf-," +
                "vtm-,jkjrx-,cdq=1,rvv-,nz=8,mp-,gdtqh-,mzg-,vnh=3,jhj=3,qjf=4,prdgn-,cn-,cqcn=4,zxf=7,gmvc=6," +
                "jvnjh=3,vnh-,qsv=6,nfdkq=5,kskj=4,fpmhbp=2,zz=5,kkk=9,cqcn-,lcrbv=7,zd-,chhq=1,ggd-,td-,dsc-,nm=2," +
                "lk-,tch=4,bz=2,fvd=1,ng-,gfq-,vlcvb=4,jxkh=7,cn-,dtv=1,mx=9,jr-,cqcn=4,qxm-,nr=1,lx-,jtc=6,sz-," +
                "pmhz-,qfl=7,vkqzk=3,fhx=6,cqnpv-,jlxf-,txbr=8,zvb-,hng-,hr=2,txbr-,mbdcz=3,vh=8,sq=5,lk-,qfmjv=8," +
                "rkr=7,sn-,jlxf-,bjbrrv=4,lfbxds-,vnf=9,cqcn-,dznp=2,bjt-,jfp=9,qghqc=1,ct-,rqxk-,mmf-,nlcv=9," +
                "ngttt=3,ljv=2,vgk-,hbh-,flf=1,nltf=6,ts=6,cq-,zsrs=7,vf-,cq-,gmvc=1,fp-,qzd=2,bfvzh-,kxsj=9,zhl-," +
                "xnj-,kx=6,xr=2,jdh-,ggdb=7,ltxz-,rm=2,svhqlj=6,xj=4,dj=5,hkh=8,mx-,zxf-,qh=9,fcn-,gbv-,qr-,drv-," +
                "qpv=2,zgx-,bc-,pmhz-,dfj=3,gt=9,zs-,gfq=6,sb=6,dhrgg=5,zg=8,nlcv-,pf=5,mkm=3,qrfx=8,bg-,kc=4,hxn=1," +
                "szj=8,ht=5,zfr-,rfh=1,bjvn-,vnh=8,gx=6,rbhl=8,gg=5,nbg=7,bjgs-,bjst-,zgrn-,kkk=1,dfj=4,hz-,td-," +
                "qkmnn-,sgnz-,qfv=2,nsp-,tms-,jmp=4,jkjrx=9,frp-,sn-,kvzd=9,gdg-,nr-,dsc=8,jvtl=2,stf-,jr-,bffjx-," +
                "cx-,gpknq-,jnzl=3,tdg=1,vjtk-,mc=2,jlxf=9,tdg-,mbdcz-,jn-,spmz=6,hqs-,xr-,fc-,ld-,thq=6,qx=6,fhgz=9," +
                "jrc-,xrqhbm=8,zvzc=5,pnblf-,hc-,tr=2,fcn=1,gcf-,hk-,xtnl=5,nqfn-,ggt-,bt-,xqqdv-,rx-,qkmnn=6,rkx=2," +
                "ht-,bjvn-,kxsj-,zsfls-,jvnjh=5,jnzl=5,dfj-,pmgjkd=7,jvtl=7,ld-,xff-,xtr-,rkr=2,zvgx=1,jflv-,rfh=7," +
                "pvr=6,qr-,zdjkk-,hkh-,dfj-,jm=3,znzbpb=2,rm=4,sz=7,ld-,lpt=3,qmfxv=4,fc-,pjtzxc-,mdps-,ld=9,zrt=1," +
                "rkr=9,xn=2,ml-,fh-,rg=9,bsxfk=7,jdkkq-,frvmt=9,dhz-,bnmft-,fbn-,brx=5,jz-,rq-,zmd=2,vcmrn=1,tl-," +
                "pjtzxc-,zvzc=5,qmfxv-,xc=9,bx-,thq-,rkr-,pk-,zs-,bslt=2,rg=7,bjq=6,mmf-,zvb-,mmjb=4,kxsj=3,ld=9," +
                "bcdqgj=6,lq-,mv=4,fpmhbp-,szm-,jjpk=4,tzz=2,svz=8,jd=1,jkr=5,cds=1,mv=7,sp=6,qh-,rkx=3,nb-,dph-," +
                "lm=5,cl=6,ttj-,gg=2,sdkkg-,ljv=9,zvb-,hnf=1,lrk-,txbr-,bsxfk-,mzqmg-,bqq-,bbb=4,nbdsxb-,nz-,ld=8," +
                "bjq=6,cbnfl=4,vf-,dm-,lpk-,nch=1,ghcd=9,fpmhbp=7,lh-,qmj-,rqxk-,rbhl=3,bjt-,bjf-,mzqmg=8,qkmnn=6," +
                "szm-,bsxfk=7,cf-,xsb-,xgq-,slt=6,hcnchc-,lpk-,hk=7,crc=3,xsb=5,cqrh-,nnm-,nhq=9,pxdlpv-,cz-,rs-," +
                "nfv=7,bsxz-,vn=2,rm-,pjtzxc=2,xjb=8,mqlfsf=3,ngkmdh=6,cf-,mdq=4,nqbl-,jtc=5,njnpq=1,jrc-,nch=5," +
                "szj=3,jr=7,qn-,gx=1,psc-,zjm=6,tzdz-,mdps-,cdq=1,bjv=9,pkp-,gzbg-,qrfx-,mc=3,cnk=9,zdpcrj=7,qfv=3," +
                "mm-,bx-,jz=9,zdjkk-,qzft-,szj-,xnj-,xj-,fz=7,bzhl=3,jkmvr-,zdjkk-,rr-,ccr=5,tr-,szj-,hjx-,bsxz-," +
                "sgqlsb=8,td=4,qkk=8,bffjx-,zxf-,zhc=4,ccr=9,gpknq-,qcm=5,tch=8,gnx=6,vf=4,qpnf-,sxs=1,vl=6,dlnc-," +
                "fvd-,gt-,hng=2,ts-,xzbll-,prdgn-,cp-,bsxfk=5,qfmjv-,bjt=1,mhr-,mzqmg=2,chhq=3,gbv-,shxm-,fc-,qkk=3," +
                "jrt=4,fhx-,bq-,hlx-,nfdkq=2,cs-,jtc-,sx=1,ggd-,jvtl-,ctbxs=4,nrrr=8,gdg=3,cdq=7,gcz=3,jc=1,ggd-,hm-," +
                "fn-,prdgn=9,zsfls=7,bbjb=6,bjf=6,tl=6,hbh=7,vsck-,pmgjkd-,knb=1,khnv-,xn-,fzfg=5,sntf-,bsxfk-,rvv-," +
                "shb-,bj-,spmz=5,bfvzh-,pjtzxc-,vkqzk=6,rx=4,vfh=2,rzd=5,hkh-,zmd=3,sb-,ltxz-,msc-,zsrs=6,lh-,jvzb=5," +
                "hhp-,rj-,bqq-,zvzc-,dhb=1,hz-,rs-,cqrh=4,gd-,gkxmct-,frvmt-,zs-,vj=4,rx=6,kgf=2,bvt=5,njnpq-,nh=4," +
                "jp-,qm-,bctx=3,fvd=1,zfr=7,bjbrrv-,cf-,hxn-,xbcm-,pld=8,km=2,zxf=3,hscr=3,fpmhbp-,xndq=7,bjq=6," +
                "bqq=1,dhb=7,mpvh-,kl-,jkmvr=1,knmdd=7,hjx=1,tr=1,pjc=9,sxs=5,qn-,ngttt=8,cqrh=4,gqgbf=8,qfv-,mk=7," +
                "jr-,sh-,fzfg-,ttx-,xc=9,xr=1,vnf-,lqh-,drv=6,dqrx=2,sn=4,hfcjfx=1,dhrgg-,msc-,zmk=5,gtnx=1,gfq=2," +
                "qpnf=8,tsqxjb=1,kk-,fh=9,zmk=2,jkr=3,sh=8,st=8,jvtl=3,xj=4,mm=7,fcn=6,bc-,gz-,cbnfl=5,ggd-,fn-," +
                "hlx=3,mm-,qr-,kvzd-,xzbll=7,nbp-,dj=9,lzzb-,pf-,qpnf=8,bcdqgj-,svhqlj-,bcdqgj-,dhb-,vv=5,bjbrrv=6," +
                "xvcp=2,rfh=1,pj-,fvd-,pfbkbm-,flf-,mp-,qfmjv-,bqhxd=6,xqqdv-,jp-,bjf=2,svz=6,zfr=3,zsm=5,sp=6,kc-," +
                "ml-,gtnx-,mv=5,fc-,krhb-,nch-,xc=9,pkp-,lm-,bbb=5,cp=4,bn=6,rk=6,lk=8,qzft=3,bsxz=3,dvn-,znx-,pzv-," +
                "rzd=2,lc-,gd-,sh=4,rzd=6,dm=4,ts=4,ftd-,xvcp-,sx-,fsgl-,bjv-,dqk-,qg-,rqxk-,bsxfk=5,rqxk=1,bvt-," +
                "qjf-,fzfg-,ftd-,rqgp-,prdgn=7,gmvc=4,qmj=4,mdps-,qp-,vfpx-,vmt-,xgq-,hcnmh-,tsqxjb-,lqfr-,jpmnk=2," +
                "tch=2,bvr=1,vmt=8,xhb=7,xhjtm=9,vjtk-,nqbl-,fp-,jvtl=5,brx=1,pld=3,vl=6,fc=1,zg=3,gfps=7,mzg-,gc=4," +
                "gckl=6,nqfn=2,bffjx=9,hxn-,tzz=8,pj=7,ts=5,fn-,jqfs=2,rqgp=3,dsc-,gkxmct=4,ttx-,xndq-,stg=8,pnblf-," +
                "shxm=5,zvb=4,gfps-,tk-,fmb=5,psc=1,gf-,pzv-,fzfg-,xgq=2,mdq=2,nfv=2,hhp=1,zxnq-,bctx=9,cv=6,vjtk-," +
                "hz=4,lx=6,nq-,dqrx-,xgq-,hr=3,shb=9,hsq-,znx-,fvd=3,xhb=7,dbs=9,jmp-,bnmft=4,jdh-,bqhxd=8,cf-," +
                "sgqlsb=3,vvf=8,bnmft=8,xzbll=8,dqrx=2,dzf-,cqnpv=1,sg-,hqs-,tcs-,nz=9,rzd-,gdg-,xjdg=2,knmdd=3," +
                "tdg=4,xtnl=5,ptmbcq-,kgx=1,frp-,vdfvp=4,nqfn-,xgq-,zvgx=2,lzs-,hk-,rjm-,fdc=6,st=4,xtr-,pf-,vx=6," +
                "qdqp=3,bfvzh-,ldvl=8,rzd-,bjst-,bvr=2,xn=7,zhc-,sb=1,lfp-,tch-,bbb=4,fhgz-,gckl=1,hm-,hbh=8,lrlj=5," +
                "rlsj-,xqqdv=1,fldg=9,mx=9,pzv-,jvtl-,qrfx=7,xjb-,zfr=4,bds=5,bj=1,bq-,bd=8,zmxkmz-,mm-,vfh=6,jmp-," +
                "jjpk=7,zhl-,sp=8,zmxkmz=1,txbr=7,xr=8,ldvl=2,ttj=4,bffjx-,zfrs-,xc=1,jrc=2,qfv=9,nrrr=7,vsck=7,rk=7," +
                "xhjtm=8,zvgx-,jxlg-,cx=2,zgrn-,zfr-,bz-,ttj=3,ht-,qfv=1,dcr-";
    }

    static String dataStr = "";

    private static int sumTheHashAlgorithms() {
        Map<String, Integer> memo = new HashMap<>();
        String[] hashAlgorithms = dataStr.split(",");
        int hashSum = 0;

        for (String hashAlgorithm : hashAlgorithms) {
            hashSum += memo.computeIfAbsent(hashAlgorithm, Day15::calculateHash);
        }

        return hashSum;
    }

    private static int calculateHash(String hashAlgorithm) {
        int total = 0;
        for (int i = 0; i < hashAlgorithm.length(); i++) {
            total = (17 * (total + (int) hashAlgorithm.charAt(i))) % 256;
        }
        return total;
    }

    private static class Lens {
        final String label;
        final int boxNumber;
        int slotNumber;
        int focalLength;

        public Lens(String lensString) {
            String[] lensArray = lensString.split("[=-]");
            this.label = parseLabel(lensArray);
            this.boxNumber = calculateHash(lensArray[0]);
            this.focalLength = parseFocalLength(lensArray);
        }

        private static int calculateHash(String str) {
            int total = 0;
            for (int i = 0; i < str.length(); i++) {
                total = (17 * (total + (int) str.charAt(i))) % 256;
            }
            return total;
        }

        public static int parseFocalLength(String lensString) {
            String[] lensArray = lensString.split("[=-]");
            return parseFocalLength(lensArray);
        }

        public static int parseFocalLength(String[] lensArray) {
            return Integer.parseInt(lensArray.length > 1 ? lensArray[1] : "0");
        }

        public static String parseLabel(String lensString) {
            String[] lensArray = lensString.split("[=-]");
            return parseLabel(lensArray);
        }

        public static String parseLabel(String[] lensArray) {
            return lensArray[0];
        }

        public int getFocusingPower() {
            return (boxNumber + 1) * slotNumber * focalLength;
        }

        public int getBoxNumber() {
            return boxNumber;
        }

        public void setFocalLength(int focalLength) {
            this.focalLength = focalLength;
        }

        public void setSlotNumber(int slotNumber) {
            this.slotNumber = slotNumber;
        }
    }

    private static Map<String, Lens> mapLabelsToLens() {
        Map<String, Lens> labelToLensMap = new LinkedHashMap<>();

        String[] lensStrings = dataStr.split(",");

        for (String lensString : lensStrings) {
            String newLabel =  Lens.parseLabel(lensString);
            int newPower = Lens.parseFocalLength(lensString);

            if (newPower == 0) {
                labelToLensMap.remove(newLabel);
            } else {
                labelToLensMap.compute(newLabel, (label, existingLens) -> {
                    if (existingLens == null) {
                        return new Lens(lensString);
                    } else {
                        existingLens.setFocalLength(newPower);
                        return existingLens;
                    }
                });
            }
        }

        return labelToLensMap;
    }

    private static void slotTheLenses(Map<String, Lens> labelToLensMap) {

        Map<Integer, AtomicInteger> boxToSlotNumber = new HashMap<>();

        labelToLensMap.values().forEach(lens -> {
            int boxNumber = lens.getBoxNumber();
            AtomicInteger slotNumber = boxToSlotNumber.computeIfAbsent(boxNumber, k -> new AtomicInteger(1));
            lens.setSlotNumber(slotNumber.getAndIncrement());
        });
    }

    public static void main(int part, boolean isDemo) {
        long startTime = System.nanoTime();
        dataStr = isDemo ? DEMO_DATA : DATA;
        int expectedResult;
        int actualResult;

        if (part == 1) {
            expectedResult = isDemo ? EXPECTED_RESULT_DEMO_PART_ONE : EXPECTED_RESULT_PART_ONE;
            actualResult = sumTheHashAlgorithms();

        } else {
            expectedResult = isDemo ? EXPECTED_RESULT_DEMO_PART_TWO : EXPECTED_RESULT_PART_TWO;
            Map<String, Lens> labelToLensMap = mapLabelsToLens();
            slotTheLenses(labelToLensMap);
            actualResult = labelToLensMap.values().stream().mapToInt(Lens::getFocusingPower).sum();
        }

        Utils.publishResult(startTime, expectedResult, actualResult);

    }
}
